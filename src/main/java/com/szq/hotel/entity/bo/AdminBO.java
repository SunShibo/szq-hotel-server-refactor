package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.util.Date;
import java.util.Set;

/**
 * 管理员信息
 */
public class AdminBO extends BaseModel {

    private Integer id;// 管理员用户
    private String mobile; // 手机号
    private String name; // 名称
    private String password; // 密码
    private String status; // 状态
    private Integer roleId; // 角色id
    private Date createTime; // 创建时间
    private Integer createUserId;//创建人
    private Date updateTime; //修改时间
    private Integer updateUserId;//修改人
    private String roleName; //角色
    private Set<String> url;//所拥有的接口
    private Set<Integer> hotels;//酒店id
    private Integer hotelId;//酒店id

    public Set<Integer> getHotels() {
        return hotels;
    }

    public void setHotels(Set<Integer> hotels) {
        this.hotels = hotels;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

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

    @Override
    public String toString() {
        return "AdminBO{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", roleId=" + roleId +
                ", createTime=" + createTime +
                ", createUserId=" + createUserId +
                ", updateTime=" + updateTime +
                ", updateUserId=" + updateUserId +
                ", roleName='" + roleName + '\'' +
                ", url=" + url +
                ", hotelId=" + hotelId +
                '}';
    }
}