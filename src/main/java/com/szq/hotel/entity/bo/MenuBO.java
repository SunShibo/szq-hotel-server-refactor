package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单信息
 */
public class MenuBO extends BaseModel {
    private Integer id; // 菜单

    private String menuName; // 菜单名称

    private Integer pid; // 父id

    private String url;//菜单路径

    private String path;//前端跳转路径

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private List<MenuBO> ch = new ArrayList<MenuBO>();

    public List<MenuBO> getCh() {
        return ch;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setCh(List<MenuBO> ch) {
        this.ch = ch;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

}
