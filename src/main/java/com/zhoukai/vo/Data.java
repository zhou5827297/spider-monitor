package com.zhoukai.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表展示数据
 */
public class Data {
    private Long total;
    private Long lastTime;
    private ItemBar itemBar = new ItemBar();
    private ItemPie itemPie = new ItemPie();
    private List<Item> items = new ArrayList<Item>();

    public static class Item {
        private Long name;
        private Long[] value = new Long[2];

        public Long getName() {
            return name;
        }

        public void setName(Long name) {
            this.name = name;
        }

        public Long[] getValue() {
            return value;
        }

        public void setValue(Long[] value) {
            this.value = value;
        }
    }

    public static class ItemBar {
        private String[] names;
        private Long[] values;

        public String[] getNames() {
            return names;
        }

        public void setNames(String[] names) {
            this.names = names;
        }

        public Long[] getValues() {
            return values;
        }

        public void setValues(Long[] values) {
            this.values = values;
        }
    }

    public static class ItemPie {
        private String[] names;
        private KeyValue[] values;

        public String[] getNames() {
            return names;
        }

        public void setNames(String[] names) {
            this.names = names;
        }

        public KeyValue[] getValues() {
            return values;
        }

        public void setValues(KeyValue[] values) {
            this.values = values;
        }

        public static class KeyValue {
            private String name;
            private Long value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Long getValue() {
                return value;
            }

            public void setValue(Long value) {
                this.value = value;
            }
        }
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public ItemBar getItemBar() {
        return itemBar;
    }

    public void setItemBar(ItemBar itemBar) {
        this.itemBar = itemBar;
    }

    public ItemPie getItemPie() {
        return itemPie;
    }

    public void setItemPie(ItemPie itemPie) {
        this.itemPie = itemPie;
    }
}
