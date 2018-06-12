package com.baizhi.entity;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private List<Menu> childMenu;
    private String id;
    private String title;
    private String iconCls;
    private String parent_id;
    private String url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<Menu> childMenu) {
        this.childMenu = childMenu;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "childMenu=" + childMenu +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", iconCls='" + iconCls + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
