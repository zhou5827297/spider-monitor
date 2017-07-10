package com.zhoukai.zookeeper;

import com.zhoukai.vo.ZookeeperData;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置文件监控
 */
@Component
public class SpiderConfig implements Watcher {
    private final static Logger LOG = LoggerFactory.getLogger(SpiderConfig.class);
    private ZooKeeper zooKeeper;
    private final static String GROUPNODE = "/spider_config";
    private final static String SPIDERSYNCDUBBOPATH = GROUPNODE + "/spider_sync_dubbo_path";

    private final static String ENCODING = "UTF-8";

    @Value("${zookeeper.server}")
    private String zookeeper_server;

    /**
     * 连接zookeeper服务器，并在集群总结点下创建EPHEMERAL类型的子节点，把服务器名称存入子节点的数据
     */
    public void connectZookeeper(String zookeeperServerHost) throws IOException, KeeperException, InterruptedException {
        zooKeeper = new ZooKeeper(zookeeperServerHost, 10000, this);
//        Stat stat = zooKeeper.exists(GROUPNODE, false);
//        if (null == stat) {
//            zooKeeper.create(GROUPNODE, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        }
    }

    public void setConfig(String path, String value) {
        try {
            connectZookeeper(zookeeper_server);
            Stat stat = zooKeeper.exists(path, false);
            if (stat == null) {
                zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            zooKeeper.setData(path, value.getBytes(ENCODING), -1);
        } catch (Exception e) {
            LOG.error("[{} - {}] set config error ,[{}]", path, value, e.getMessage());
        } finally {
            try {
                closeZookeeper();
            } catch (InterruptedException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public List<ZookeeperData> getConfig(String path) {
        List<ZookeeperData> datas = new ArrayList<>();
        try {
            connectZookeeper(zookeeper_server);
            putPathValueForData(path, datas);
        } catch (Exception e) {
            LOG.error("[{}] get config error ,[{}]", path, e.getMessage());
        } finally {
            try {
                closeZookeeper();
            } catch (InterruptedException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return datas;
    }

    private void putPathValueForData(String path, List<ZookeeperData> datas) {
        try {
            Stat stat = zooKeeper.exists(path, false);
            if (stat != null) {
                byte[] data = zooKeeper.getData(path, false, stat);
                if (data == null) {
                    data = "".getBytes(ENCODING);
                }
                String value = new String(data, ENCODING);
                ZookeeperData zookeeperData = new ZookeeperData();
                zookeeperData.setName(path);
                zookeeperData.setValue(value);
                zookeeperData.setStat(stat);
                datas.add(zookeeperData);
            }
        } catch (Exception e) {
            LOG.error("[{}] get node error ,[{}]", path, e.getMessage());
        }
    }


    public List<ZookeeperData> getAll(String path) {
        List<ZookeeperData> datas = new ArrayList<>();
        try {
            connectZookeeper(zookeeper_server);
            putPathValueForData(path, datas);

            List<String> childrens = zooKeeper.getChildren(path, false);
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            for (String childPath : childrens) {
                String absPath = path + "/" + childPath;
                putPathValueForData(absPath, datas);
            }

        } catch (Exception e) {
            LOG.error("[{}] get config error ,[{}]", path, e);
        } finally {
            try {
                closeZookeeper();
            } catch (InterruptedException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return datas;
    }

    /**
     * 关闭于zookeeper服务器的连接
     */
    public void closeZookeeper() throws InterruptedException {
        if (null != zooKeeper) {
            zooKeeper.close();
        }
    }

    /**
     * 重启zk客户端
     */
    public void restart() {
        try {
            closeZookeeper();
            connectZookeeper(zookeeper_server);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 启动站点监控
     */
    public void start() {
        try {
            this.connectZookeeper(zookeeper_server);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            restart();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.info("listen zookeeper event -----eventType:[{}]，path[{}]", event.getType(), event.getPath());
        //列表发生改变
        if (event.getType() == Event.EventType.NodeChildrenChanged && event.getPath().equals(SPIDERSYNCDUBBOPATH)) {

        }
        //数据发生改变
        if (event.getType() == Event.EventType.NodeDataChanged && event.getPath().startsWith(SPIDERSYNCDUBBOPATH)) {

        }
    }
}
