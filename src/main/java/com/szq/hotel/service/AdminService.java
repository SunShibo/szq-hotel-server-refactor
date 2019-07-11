package com.szq.hotel.service;


import com.szq.hotel.common.constants.LanguageTalentConstants;
import com.szq.hotel.dao.AdminDAO;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.HotelBO;
import com.szq.hotel.entity.bo.MenuBO;
import com.szq.hotel.entity.bo.RoleBO;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.MD5Util;
import com.szq.hotel.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("AdminService")
@Transactional
public class AdminService {

    @Resource
    private AdminDAO adminDAO;

    /**
     * 根据手机号查找管理员信息
     * 管理员登录
     *
     * @param mobile
     * @return
     */
    public AdminBO queryAdminInfoByMobile(String mobile) {
        AdminBO adminBO = adminDAO.queryAdminInfoByMobile(mobile);
        if (adminBO != null) {
            List<MenuBO> urls = adminDAO.getMenuByRoleId(adminBO.getRoleId());
            Set<String> set = new HashSet<String>();
            if (urls != null && urls.size() > 0) {
                for (MenuBO menuBO : urls) {
                    if (menuBO.getUrl() != null && !StringUtils.isEmpty(menuBO.getUrl())) {
                        String[] split = menuBO.getUrl().split(",");
                        for (String str : split) {
                            set.add(str);
                        }
                    }
                }
            }
            adminBO.setUrl(set);


            List<HotelBO> HotelBOS = adminDAO.getHotelByRoleId(adminBO.getRoleId());
            Set<Integer> hotelSet = new HashSet<Integer>();
            if (HotelBOS != null && HotelBOS.size() > 0) {
                for (HotelBO hotelBO : HotelBOS) {
                    hotelSet.add(hotelBO.getId());
                }
            }
            adminBO.setHotels(hotelSet);
        }

        return adminBO;
    }


    /**
     * 根据当前登陆用户的角色id查询对应的权限
     *
     * @param roleId 角色id
     * @return 登陆用户对应的权限
     */
    public List<MenuBO> getRoleMenuSuccess(Integer roleId) {
        List<MenuBO> menuBOS = adminDAO.getMenuByRoleId(roleId);
        if(menuBOS!=null && menuBOS.size()>0) {
            for (int i = 0; i < menuBOS.size(); i++) {
                MenuBO menuBO = menuBOS.get(i);
                for(int x=0;x<menuBOS.size();x++){
                    if(menuBO.getPid().equals(menuBOS.get(x).getId())){
                        menuBOS.get(x).getChildren().add(menuBO);
                        menuBOS.remove(i);
                        --i;
                        break;
                    }
                }
            }
        }
        return menuBOS;
    }

    /**
     * 根据当前登陆用户的角色id查询对应的权限
     *
     * @param roleId 角色id
     * @return 登陆用户对应的权限
     */
    public List<HotelBO> getRoleHotelSuccess(Integer roleId) {
        List<HotelBO> hotelBOS = adminDAO.getHotelByRoleId(roleId);
//        if (hotelBOS != null && hotelBOS.size() > 0) {
//            for (int i = 0; i < hotelBOS.size(); i++) {
//                HotelBO hotelBO = hotelBOS.get(i);
//                for (int x = 0; x < hotelBOS.size(); x++) {
//                    if (hotelBO.getId().equals(hotelBOS.get(x).getId())) {
//                        hotelBOS.remove(i);
//                        --i;
//                        break;
//                    }
//                }
//            }
//        }
        return hotelBOS;
    }

    //获取所有菜单权限
    public List<MenuBO> getMenuList(){
        return adminDAO.getMenuList();
    }

    /**
     * 管理员注册
     *
     * @param admin
     * @return
     */
    public int adminRegister(AdminBO admin) {
        //初始化管理员状态
        admin.setStatus(LanguageTalentConstants.NORMAL);// 状态 normal:正常  logout：注销
        //加密密码
        admin.setPassword(MD5Util.digest(admin.getPassword()));
        return adminDAO.adminRegister(admin);
    }


    /**
     * 判断角色名称是否注册过
     *
     * @param roleName
     * @return
     */
    public Integer selectCountByRoleName(String roleName) {
        return adminDAO.selectCountByRoleName(roleName);
    }

    /**
     * 查找用户名是否注册过
     *
     * @param name
     * @return
     */
    public int selectCountByName(String name) {
        return adminDAO.selectCountByMobile(name);
    }

    /**
     * 查找手机号是否注册过
     *
     * @param mobile
     * @return
     */
    public int selectCountByMobile(String mobile) {
        return adminDAO.selectCountByMobile(mobile);
    }

    /**
     * 添加角色信息
     *
     * @param roleBO
     * @return 返回角色id
     */
    public Integer addRole(RoleBO roleBO) {
        return adminDAO.addRole(roleBO);
    }

    /**
     * wy查询所有的角色信息
     *
     * @return 角色集合
     */
    public List<RoleBO> getRoleList() {
        return adminDAO.getRoleList();
    }

    /**
     * 查询角色下是否有用户
     */
    public Integer checkRoleUser(String roleIds) {
        Integer[] idArr = JsonUtils.getIntegerArray4Json(roleIds);
        for (int i = 0; i < idArr.length; i++) {
            int count = adminDAO.checkRoleUser(new Integer(idArr[i]));
            if (count > 0) {
                return idArr[i];
            }
        }
        return null;
    }

    /**
     * 根据条件分页查询用户信息
     * wy
     * userId 用户id
     * roleId 角色id
     * userName 用户名
     *
     * @return
     */
    public List<AdminBO> getAdminByCond(Map<String, Object> map) {
        return adminDAO.getAdminByCond(map);
    }

    /**
     * 根据条件查询用户信息总数量
     * wy
     * userId 用户id
     * roleId 角色id
     * userName 用户名
     *
     * @return
     */
    public Integer getAdminCountByCond(Map<String, Object> map) {
        return adminDAO.getAdminCountByCond(map);
    }

    /**
     * wy
     * 根据角色id查询角色信息
     *
     * @param roleId
     * @return
     */
    public RoleBO getRoleById(Integer roleId) {
        return adminDAO.getRoleById(roleId);
    }

    /**
     * 向角色权限表中添加数据
     *
     * @param roleId    角色id
     * @param menuIdArr 菜单id数组
     * @return
     */
    public int addRoleMenu(Integer roleId, Integer[] menuIdArr) {
        return adminDAO.addRoleMenu(roleId, menuIdArr);
    }

    /**
     * 向角色权限表中添加数据
     *
     * @param roleId     角色id
     * @param hotelIdArr 酒店id数组
     * @return
     */
    public int addRoleHotel(Integer roleId, Integer[] hotelIdArr) {
        return adminDAO.addRoleHotel(roleId, hotelIdArr);
    }


    /**
     * 根据角色id删除角色
     *
     * @param roleIds 角色id
     */
    public boolean delRoleById(Integer[] roleIds) {
        return adminDAO.delRoleById(roleIds);
    }


    /**
     * 修改一个角色的菜单权限信息
     *
     * @param roleBO    这个角色的信息
     * @param menuIdArr 菜单id
     */
    public boolean updRoleToMenu(RoleBO roleBO, String menuIdArr) {
        Integer[] menuIds = JsonUtils.getIntegerArray4Json(menuIdArr);
        //删除这个角色所有权限
        adminDAO.delRoleMenuByRoleId(roleBO.getId());
        //添加权限
        return adminDAO.addRoleMenu(roleBO.getId(), menuIds) > 0;
    }

    /**
     * 修改一个角色的酒店权限信息
     *
     * @param roleBO     这个角色的信息
     * @param hotelIdArr 菜单id
     */
    public boolean updRoleToHotel(RoleBO roleBO, String hotelIdArr) {
        Integer[] hotelIds = JsonUtils.getIntegerArray4Json(hotelIdArr);
        //删除这个角色所有权限
        adminDAO.delRoleHotelByRoleId(roleBO.getId());
        //添加权限
        return adminDAO.addRoleHotel(roleBO.getId(), hotelIds) > 0;
    }


    /**
     * wy根据用户id删除用户信息
     *
     * @param idArr
     * @return
     */
    public boolean delAdminById(Integer[] idArr) {
        return adminDAO.delAdminById(idArr);
    }

    /**
     * wy
     * 修改admin用户信息
     *
     * @param param 用户信息
     * @return 是否修改成功
     */
    public boolean updateAdminUser(AdminBO param) {
        //加密密码
        param.setPassword(MD5Util.digest(param.getPassword()));
        return adminDAO.updateAdminUser(param) > 0;
    }

    /**
     * 根据角色id修改角色名称
     *
     * @param roleId   角色id
     * @param roleName 角色名称
     */
    public void updateRoleNameByRoleId(Integer roleId, String roleName) {
        adminDAO.updateRoleNameByRoleId(roleId, roleName);
    }

    /**
     * 查询角色对应的菜单
     *
     * @param roleName 角色名称 为null查询所有
     * @return
     */
    public List<RoleBO> getRoleByRoleName(String roleName) {
        //查询所有角色
        List<RoleBO> roleList = adminDAO.getRoleByName(roleName);
        //循环查询角色拥有的菜单
        for (RoleBO role : roleList) {
            List<MenuBO> menus = adminDAO.getMenuByRoleId(role.getId());
            role.setMenus(menus);
        }
        //循环查询角色拥有的酒店
        for (RoleBO role : roleList) {
            List<HotelBO> hotelBOS = adminDAO.getHotelByRoleId(role.getId());
            role.setHotels(hotelBOS);
        }
        return roleList;
    }


}
