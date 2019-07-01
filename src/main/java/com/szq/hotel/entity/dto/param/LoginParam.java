package com.szq.hotel.entity.dto.param;

import com.szq.hotel.common.base.BaseModel;

import java.io.Serializable;

/**
 * Created by sunshibo on 2016/1/4.
 */
public class LoginParam extends BaseModel implements Serializable {


    private static final long serialVersionUID = 8146980880302786091L;

    /**
     * 登录账号
     */
    private String account ;

    /**
     * 密码
     */
    private String password ;
    
    /**
     * 登录唯一标识码
     */
    private String loginId;

    /**
     * 设备唯一码
     */
    private String uniqueCode ;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

}
