package com.zhoukai.siteconfig;

import com.zhoukai.util.FileUtil;
import com.zhoukai.util.ProcessUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * 配置文件服务
 */
@Service
public class SiteConfigService {

    @Value("${site.path.local}")
    private String PATH;

    @Value("${site.path.svn}")
    private String SVNPATH;

    private final static String SVNSYNC = "svn co %s %s";

    private final static String SVNCREATE = "svn add %s";

    private final static String SVNUPDATE = "svn update %s";

    private final static String SVNDELETE = "svn delete %s";

    private final static String SVNCOMMIT = "svn commit -m 'spider-monitor' %s ";

    private final static Set<String> SAVESITE = Collections.synchronizedSet(new HashSet<String>());

//    protected final Map<String, String> SITEDATA = new ConcurrentHashMap<>();

//
//
//    /**
//     * 同步本地文件到内存中
//     */
//    public synchronized void syncFileToMem() {
//        List<File> allFiles = new ArrayList<File>();
//        files(allFiles, new File(PATH));
//        for (File file : allFiles) {
//            SITEDATA.put(file.getAbsolutePath(), new String(FileUtil.getBytes(file.getPath())));
//        }
//    }
//
//    private List<File> files(List<File> ret, File file) {
//        if (file.isDirectory()) {
//            if (!".svn".equals(file.getName())) {
//                File[] fs = file.listFiles();
//                for (File f : fs) {
//                    files(ret, f);
//                }
//            }
//        } else if (file.getName().endsWith(".json")) {
//            ret.add(file);
//        }
//        return ret;
//    }

    /**
     * 保存文件
     */
    public boolean saveSiteFile(String path, String content, boolean isNew) {
        if (path.endsWith(".json")) {
            String filePath = path.substring(0, path.lastIndexOf(File.separator));
            String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
            FileUtil.writeFile(content.getBytes(), filePath, fileName);
        } else {
            FileUtil.mkdirs(path);
        }

        if (new File(path).exists()) {
            //提交svn
            if (isNew) {
                ProcessUtils.execute(String.format(SVNCREATE, path));
            }
            ProcessUtils.execute(String.format(SVNCOMMIT, path));
            return true;
        }
        return false;
    }

    /**
     * 同步svn文件
     */
    public boolean svnSync() {
        ProcessUtils.execute(String.format(SVNSYNC, SVNPATH, PATH));
        return true;
    }


    /**
     * 重命名文件
     */
    public boolean renameSiteFile(String targetPath, String originPath) {
        File origin = new File(originPath);
        File target = new File(targetPath);
        origin.renameTo(target);

        ProcessUtils.execute(String.format(SVNDELETE, originPath));
        ProcessUtils.execute(String.format(SVNCOMMIT, originPath));

        if (origin.exists()) {
            origin.delete();
        }


        ProcessUtils.execute(String.format(SVNCREATE, targetPath));
        ProcessUtils.execute(String.format(SVNCOMMIT, targetPath));


        return true;
    }

    /**
     * 删除文件
     */
    public boolean deleteSiteFile(String path) {
        ProcessUtils.execute(String.format(SVNDELETE, path));
        ProcessUtils.execute(String.format(SVNCOMMIT, path));
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        return true;
    }

    /**
     * 获取指定的树形结构
     */
    public List<TreeNode> getTreeNode() {
        return getTreeNode(null);
    }

    /**
     * 获取指定的树形结构
     */
    public List<TreeNode> getTreeNode(String condition) {
        TreeNode treeNode = new TreeNode();
        treeNodes(treeNode, new File(PATH), condition);
        return (List<TreeNode>) treeNode.getChildren();
    }

    /**
     * 获取指定的树形结构
     */
    public String getSiteContent(String path) {
        byte[] bs = FileUtil.getBytes(path);
        if (bs == null) {
            return "";
        }
        return new String(bs);
    }

    /**
     * 读取所有配置文件
     */
    private void treeNodes(TreeNode parentTreeNode, File file, String condition) {
        if (file.isDirectory()) {
            if (!".svn".equals(file.getName())) {

                TreeNode treeNode = new TreeNode();
                treeNode.setId(file.getAbsolutePath());
                treeNode.setText(file.getName());
                treeNode.setIcon("folder");

                File[] fs = file.listFiles();
                //上级也是目录
                if (parentTreeNode.getId() != null && file.getParentFile().isDirectory()) {
                }

                if (parentTreeNode.getChildren() == null) {
                    List<TreeNode> treeNodes = new ArrayList<>();
                    parentTreeNode.setChildren(treeNodes);
                }
                ((List<TreeNode>) parentTreeNode.getChildren()).add(treeNode);
                for (File f : fs) {
                    treeNodes(treeNode, f, condition);
                }
            }
        } else if (file.getName().endsWith(".json")) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(file.getAbsolutePath());
            treeNode.setText(file.getName());
            treeNode.setIcon("file");
            treeNode.setChildren(false);
            if (parentTreeNode.getChildren() == null) {
                List<TreeNode> treeNodes = new ArrayList<>();
                parentTreeNode.setChildren(treeNodes);
            }
            if (condition == null) {
                ((List<TreeNode>) parentTreeNode.getChildren()).add(treeNode);
            } else {
                if (treeNode.getId().contains(condition)) {
                    ((List<TreeNode>) parentTreeNode.getChildren()).add(treeNode);
                } else {
                    String content = getSiteContent(treeNode.getId());
                    if (content.contains(condition)) {
                        ((List<TreeNode>) parentTreeNode.getChildren()).add(treeNode);
                    }
                }
            }
        }
    }

    /*
     *节点模型
     */
    public static class TreeNode {

        private String id;
        private String text;
        private String type;
        private String icon;
        private Object children;
        private state state;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Object getChildren() {
            return children;
        }

        public void setChildren(Object children) {
            this.children = children;
        }

        public TreeNode.state getState() {
            return state;
        }

        public void setState(TreeNode.state state) {
            this.state = state;
        }

        public static class state {
            private boolean opened = true;
            private boolean disabled = true;

            public boolean isOpened() {
                return opened;
            }

            public void setOpened(boolean opened) {
                this.opened = opened;
            }

            public boolean isDisabled() {
                return disabled;
            }

            public void setDisabled(boolean disabled) {
                this.disabled = disabled;
            }
        }
    }

}
