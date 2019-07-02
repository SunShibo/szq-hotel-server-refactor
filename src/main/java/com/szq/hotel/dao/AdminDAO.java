package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.MenuBO;
import com.szq.hotel.entity.bo.RoleBO;
import com.szq.hotel.entity.dto.AdminDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminDAO {

    /**
     * 通过手机号查找管理员信息
     *
     * @param mobile
     * @return UserDO
     */
    AdminBO queryAdminInfoByMobile(String mobile);

    /**
     * 注册管理员信息
     *
     * @param admin
     * @return
     */
    int adminRegister(AdminBO admin);

    /**
     * 判断角色名是否注册过
     *
     * @param roleName 角色名称
     * @return
     */
    Integer selectCountByRoleName(String roleName);

    /**
     * 查找用户手机号是否注册过
     *
     * @param mobile
     * @return
     */
    int selectCountByMobile(String mobile);

    /**
     * 添加角色信息
     *
     * @param roleName
     * @return 返回注册后的id
     */
    int addRole(String roleName);

    /**
     * 查询所有角色
     *
     * @return 所有角色
     */
    List<RoleBO> getRoleList();

    /**
     * wy根据角色id查询菜单信息
     * @return 所有权限
     */
    List<MenuBO> getMenuByRoleId(Integer roleId);

    /**wy
     * 向角色权限表中添加数据
     * @param roleId 角色id
     * @param menuIdArr 菜单id数组
     * @return
     */
    int addRoleMenu(@Param("roleId") Integer roleId, @Param("menuIdArr") Integer[] menuIdArr);
    /**
     * wy
     * 根据角色姓名 查询角色信息
     * 角色姓名为null 查询所有
     * @param roleName
     * @return
     */
    List<RoleBO> getRoleByName(String roleName);

    /**
     * wy
     * 根据角色id 查询角色信息
     * @param id
     * @return
     */
    RoleBO getRoleById(Integer id);

    /**
     * 根据角色id删除角色信息
     *
     * @param roleId 角色id
     */
    boolean delRoleById(Integer roleId);

    /**
     * wy
     * 批量删除
     * 根据用户id删除用户信息
     * @param idArr 用户id
     * @return
     */
    boolean delAdminById(@Param("idArr") Integer[] idArr);

    /**
     * 查询用户信息
     *
     * @param map
     * @return
     */
    List<AdminDTO> getAdmin(Map<String, Object> map);

    /**
     * 根据角色id删除对应的权限
     *
     * @param roleId
     */
    void delRoleMenuByRoleId(Integer roleId);


    //wy查询角色下是否有用户
    int checkRoleUser(Integer roleId);

    /**
     * wy
     * 根据角色id修改角色信息
     * @param roleId
     * @param roleName
     */
    void updateRoleNameByRoleId(@Param("roleId") Integer roleId, @Param("roleName") String roleName);

    /**
     * wy
     * 修改admin用户信息
     * @param adminBO 修改信息
     */
    Integer updateAdminUser(AdminBO adminBO);

    /**
     * 根据条件分页查询用户信息
     * wy
     * userId 用户id
     * roleId 角色id
     * userName 用户名
     * @return
     */
    List<AdminBO> getAdminByCond(Map<String, Object> map);

    /**
     * 根据条件查询用户信息总数
     * wy
     * userId 用户id
     * roleId 角色id
     * userName 用户名
     * @return
     */
    Integer getAdminCountByCond(Map<String, Object> map);



}
