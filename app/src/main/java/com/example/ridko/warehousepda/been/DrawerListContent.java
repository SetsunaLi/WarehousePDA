package com.example.ridko.warehousepda.been;

import com.example.ridko.warehousepda.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mumu on 2018/3/29.
 */

public class DrawerListContent {

    //An array of sample (Settings) items.
    public static List<DrawerItem> ITEMS = new ArrayList<>();

    //A map of sample (Settings) items, by ID.
    public static Map<String, DrawerItem> ITEM_MAP = new HashMap<>();
    static {
        // Add items.
        //addItem(new DrawerItem("1", "Home", R.drawable.app_icon));
        addItem(new DrawerItem("1", "主页", R.drawable.about));
        addItem(new DrawerItem("2", "特殊入库", R.drawable.about));
        addItem(new DrawerItem("3", "解绑与绑定", R.drawable.about));
        addItem(new DrawerItem("4", "剪布管理", R.drawable.about));
        addItem(new DrawerItem("5", "查布管理", R.drawable.about));
        addItem(new DrawerItem("6", "备货管理", R.drawable.about));
        addItem(new DrawerItem("7", "出库管理", R.drawable.about));
        addItem(new DrawerItem("8", "设备管理", R.drawable.about));
        addItem(new DrawerItem("9", "关于", R.drawable.about));
        addItem(new DrawerItem("10", "测试Demo", R.drawable.about));
        addItem(new DrawerItem("11", "连接PDA", R.drawable.about));
    }

    /**
     * Method to add a new item
     *
     * @param item - Item to be added
     */
    private static void addItem(DrawerItem item) {

        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A Drawer item represents an entry in the navigation drawer.
     */
    public static class DrawerItem {
        public String id;
        public String content;
        public int icon;

        public DrawerItem(String id, String content, int icon_id) {
            this.id = id;
            this.content = content;
            this.icon = icon_id;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
