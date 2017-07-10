package com.zhoukai.vo;

import org.apache.zookeeper.data.Stat;

/**
 * zk数据结果包
 */
public class ZookeeperData {
    private String name;
    private String value;
    private Stat stat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }
}
