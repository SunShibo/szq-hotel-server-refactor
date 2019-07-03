package com.szq.hotel.web.controller;


import com.szq.hotel.common.constants.SysConstants;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.RoleBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.AdminService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.MD5Util;
import com.szq.hotel.util.StringUtils;
import com.szq.hotel.util.redisUtils.RedissonHandler;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 管理员
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseCotroller {
    @Resource
    private AdminService adminService ;

    /**
     * 管理员登录
     * @param request
     * @param response
     * @param mobile 手机号
     * @param password 密码
     */
    @RequestMapping("/adminLogin")
    public void adminLogin(HttpServletRequest request, HttpServletResponse response,String mobile,String password){
        //验证参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常！")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证账号
        AdminBO adminBO = adminService.queryAdminInfoByMobile(mobile);
        if(adminBO == null||!MD5Util.digest(password).equals(adminBO.getPassword())){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000091" , "用户名密码错误")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        adminBO.setPassword("");
        String uuid = UUID.randomUUID().toString();
        //登陆客户信息放入Redis缓存
        super.putLoginAdmin(uuid,adminBO);
        //uuid 存入cookie
        super.setCookie(response, SysConstants.CURRENT_LOGIN_CLIENT_ID, uuid, 60*60*24);

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(adminBO)) ;
        super.safeJsonPrint(response, result);
        return;
    }

    /**
     * 创建管理员
     * @param request
     * @param response
     */
    @RequestMapping("/adminRegister")
    public void adminRegister(HttpServletRequest request, HttpServletResponse response, AdminBO admin){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(admin == null || StringUtils.isEmpty(admin.getPassword())
                || StringUtils.isEmpty(admin.getMobile()) || StringUtils.isEmpty(admin.getName()) || admin.getRoleId()==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        //判断手机号是否注册过
        int count = adminService.selectCountByMobile(admin.getMobile());
        if(count > 0 ){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000093" , "账号已存在")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        // 判断用户名是否注册过
        int name = adminService.selectCountByName(admin.getName());
        if(name > 0 ){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000093" , "用户名已存在")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        //注册管理员
        adminService.adminRegister(admin);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("")) ;
        super.safeJsonPrint(response, result);
    }

    /**
     * 根据条件查询管理员信息
     * @param request
     * @param response
     * @param userId 用戶id
     * @param roleId 角色id
     * @param userName 用戶名
     */
    @RequestMapping("/getAdminById")
    public void getAdminById(HttpServletRequest request,HttpServletResponse response,
                             Integer userId,Integer roleId,String userName,Integer pageNo,Integer pageSize){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //查询信息
        Map<String, Object> map = new HashMap<String, Object>();
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        if  (queryInfo != null) {
            map.put("pageNo", queryInfo.getPageOffset());
            map.put("pageSize", queryInfo.getPageSize());
        }
        map.put("userId",userId);
        map.put("roleId",roleId);
        map.put("userName",userName);
        //分页的用户信息
        List<AdminBO> adminBOList = adminService.getAdminByCond(map);
        //用户信息总数
        Integer count=adminService.getAdminCountByCond(map);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("adminBOList",adminBOList);
        resultMap.put("count",count);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap)) ;
        super.safeJsonPrint(response, result);
    }
    /**
     * 批量删除用户
     * @param request
     * @param response
     * @param idArr 需要刪除的用戶的id
     */
    @RequestMapping("/delAdmins")
    public void delAdmin(HttpServletRequest request,HttpServletResponse response,String idArr){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        // 非空判断
        if(StringUtils.isEmpty(idArr)||idArr.length()<=2){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        //转换为数组 批量删除
        Integer[] ids= JsonUtils.getIntegerArray4Json(idArr);
        adminService.delAdminById(ids);
        String date= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除成功"));
        super.safeJsonPrint(response,date);
        return;
    }

    /**
     * 修改admin用戶信息
     * @param request
     * @param response
     * @param param
     * @param
     */
    @RequestMapping("/updateAdminUser")
    public void updateAdminUser(HttpServletRequest request, HttpServletResponse response, AdminBO param){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(param.getId()==null||param.getId().equals("")||param.getMobile()==null||param.getMobile().equals("")||param.getName()==null||param.getName().equals("")){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }

        //验证手机号
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("userId",param.getId());
        map.put("pageNo",0);
        map.put("pageSize",1);
        List<AdminBO> adminBOS=adminService.getAdminByCond(map);
        if(adminBOS==null||adminBOS.size()==0){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return;
        }
        String oldMobile=adminBOS.get(0).getMobile();
        //不等于旧手机号则验证是否重复
        if(!param.getMobile().equals(oldMobile)){
            int count = adminService.selectCountByMobile(param.getMobile());
            if(count > 0 ){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000093" , "账号已存在")) ;
                super.safeJsonPrint(response , result);
                return ;
            }
        }
        adminService.updateAdminUser(param);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success( "修改成功")) ;
        super.safeJsonPrint(response , result);
    }


    /**
     * 修改密码
     * @param request
     * @param response
     * @param param
     */
    @RequestMapping("/changePassword")
    public void changePassword(HttpServletRequest request, HttpServletResponse response, AdminBO param){
        // 非空判断
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(param.getId()==null||param.getId().equals("")||param.getPassword()==null||param.getPassword().equals("")){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        adminService.updateAdminUser(param);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success( "修改成功")) ;
        super.safeJsonPrint(response , result);

    }


    /**
     * 创建角色
     * 并赋予权限
     * @param request
     * @param response
     * @param roleName  角色名称
     * @param menuIds  权限ids
     */
    @RequestMapping("/addRoleGrantAuthority")
    public void addRoleGrantAuthority(HttpServletRequest request,HttpServletResponse response,
                 String roleName,String menuIds){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //参数验证
        if(StringUtils.isEmpty(roleName) || StringUtils.isEmpty(menuIds)){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常"+"roleName:"+roleName +"menuIds:"+menuIds)) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        // 判断角色名称是否存在
        Integer nameCount = adminService.selectCountByRoleName(roleName);
        if(nameCount>0){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000092" , "该角色已存在")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        //创建角色 返回角色id
        RoleBO roleBO=new RoleBO();
        roleBO.setRoleName(roleName);
        adminService.addRole(roleBO);
        System.err.println(roleBO.getId());
        //添加权限
        Integer[] menuIdArr= JsonUtils.getIntegerArray4Json(menuIds);
        adminService.addRoleMenu(roleBO.getId(),menuIdArr);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加成功")) ;
        super.safeJsonPrint(response , result);

    }

    /**
     * 角色信息列表查询
     * 角色名模糊查询
     * 查询角色对应的权限
     * @param request
     * @param response
     */
    @RequestMapping("/getAllRoleMenu")
    public void getAllRoleMenu(HttpServletRequest request,HttpServletResponse response,String roleName){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        List<RoleBO> ros = adminService.getRoleByRoleName(roleName);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(ros));
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(json)) ;
        super.safeJsonPrint(response, result);
    }


    /**
     * 编辑对应角色的权限
     * @param request
     * @param response
     * @param roleId  角色id
     * @param menuIds  权限id
     */
    @RequestMapping("/grantAuthority")
    public void grantAuthority(HttpServletRequest request,HttpServletResponse response,Integer roleId,String menuIds,String roleName){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(StringUtils.isEmpty(String.valueOf(roleId)) || StringUtils.isEmpty(String.valueOf(menuIds))|| StringUtils.isEmpty(roleName)){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        //验证角色名
        RoleBO roleBO = adminService.getRoleById(roleId);
        if(!roleBO.getRoleName().equals(roleName)){
            // 不一致查询是否重复
            Integer nameCount = adminService.selectCountByRoleName(roleName);
            if(nameCount>0){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000092" , "该角色已存在")) ;
                super.safeJsonPrint(response , result);
                return ;
            }
        }
        //修改角色姓名信息
        adminService.updateRoleNameByRoleId(roleId,roleName);

        //修改角色的权限
        adminService.updRoleToMenu(roleBO,menuIds);

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改成功")) ;
        super.safeJsonPrint(response, result);
    }

    /**
     * 角色下拉框
     * 查询所有角色
     */
    @RequestMapping("/getRoleList")
    public void getRoleList(HttpServletRequest request,HttpServletResponse response){
        List<RoleBO> roleList = adminService.getRoleList();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(roleList)) ;
        super.safeJsonPrint(response, result);
    }

    /**
     * 删除角色信息 根据角色id
     * @param request
     * @param roleIds
     */
    @RequestMapping("/delRoleByIds")
    public void delRoleByIds(HttpServletRequest request,HttpServletResponse response,String roleIds){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(StringUtils.isEmpty(String.valueOf(roleIds)) || roleIds.length()<=2){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        //判断该角色下是否用户
        Integer id = adminService.checkRoleUser(roleIds);
        if(id!=null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001",id+"该角色下存在用户" )) ;
            super.safeJsonPrint(response , result);
            return;
        }
        //删除成功
        Integer[] roleIdArr=JsonUtils.getIntegerArray4Json(roleIds);
        adminService.delRoleById(roleIdArr);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除成功")) ;
        super.safeJsonPrint(response, result);

    }


    //退出登录
    @RequestMapping("/exitLogin")
    public void exit(HttpServletResponse response,HttpServletRequest  request){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //退出登录
        String clientLoginID = super.getClientLoginID(request);
        if (StringUtils.isEmpty(clientLoginID)) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "没有获取到clientLoginID！")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        String key = super.createKey(clientLoginID, SysConstants.CURRENT_LOGIN_USER);
        //从redis中删除用户信息
        RedissonHandler.getInstance().delete(key);
        //删除cookie
        super.removeCookie(request,response,key);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("" )) ;
        super.safeJsonPrint(response , result);
        return ;
    }





}
