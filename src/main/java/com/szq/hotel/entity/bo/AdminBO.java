package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.util.Date;
import java.util.Set;

/**
 * Created by hexiaowen on 2018/11/5.
 */
public class AdminBO extends BaseModel {

    private Integer id;// 管理员用户

    private String mobile; // 手机号

    private String name; // 名称

    private String password; // 密码

    private String status; // 状态

    private Integer roleId; // 角色id

    private Date createTime; // 创建时间

    private String roleName; //角色

    private Set<String> url;//所拥有的接口

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<String> getUrl() {
        return url;
    }

    public void setUrl(Set<String> url) {
        this.url = url;
    }
}