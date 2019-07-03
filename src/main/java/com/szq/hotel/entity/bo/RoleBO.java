package com.szq.hotel.entity.bo;


import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.util.Date;
import java.util.List;
/**
 * 角色信息
 */
public class RoleBO extends BaseModel {
    private Integer id; // 角色
    private String roleName; //角色名称
    private Integer createUserId;//创建时间
    private Integer updateUserId;//修改时间
    private Date createTime; //创建时间按
    private Date updateTime; // 修改时间
    private List<MenuBO> menus;//该角色拥有的菜单权限

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<MenuBO> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuBO> menus) {
        this.menus = menus;
    }
}
