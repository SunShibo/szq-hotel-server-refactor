package com.szq.hotel.entity;


import com.szq.hotel.common.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

public class UserDO extends BaseModel implements Serializable {

		/**冻结状态*/
		public static final String STATUS_FREEZE = "status_freeze" ;
		/**正常状态*/
		public static final String STATUS_NORMAL = "status_normal" ;
		/**未激活*/
		public static final String STATUS_INACTIVE = "status_inactive" ;

		/**男*/
		public static final String GENDER_MALE = "gender_male" ;
		/**女*/
		public static final String GENDER_FEMALE = "gender_female" ;

		private int id;		// ID
		private String username;	// 用户名
		private String password;	// 密码
		private String name;		// 姓名
		private String nickname;	// 昵称
		private String gender;		// 性别（female-女；male-男）
		private Date birthday;	// 生日
		private String location;	// 地址
		private String signature;	// 签名
		private String phoneNumber;	// 手机号
		private String email;		// 电子邮箱
		private String wechat;		// 微信号
		private String sinaWeibo;		// 新浪微博号
		private String twiter;		// twiter号
		private String facebook;		// facbook 号
		private String linkedin;		// linkedin 号
		private Date createTime;		// 创建时间
		private Date modifyTime;		// 修改时间
		private String status ; // 用户状态
		private String headPortrait;		// 头像路径
		private String country ; //国家

		public UserDO() {
			// TODO Auto-generated constructor stub
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public Date getBirthday() {
			return birthday;
		}

		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getWechat() {
			return wechat;
		}

		public void setWechat(String wechat) {
			this.wechat = wechat;
		}

		public String getSinaWeibo() {
			return sinaWeibo;
		}

		public void setSinaWeibo(String sinaWeibo) {
			this.sinaWeibo = sinaWeibo;
		}

		public String getTwiter() {
			return twiter;
		}

		public void setTwiter(String twiter) {
			this.twiter = twiter;
		}

		public String getFacebook() {
			return facebook;
		}

		public void setFacebook(String facebook) {
			this.facebook = facebook;
		}

		public String getLinkedin() {
			return linkedin;
		}

		public void setLinkedin(String linkedin) {
			this.linkedin = linkedin;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getModifyTime() {
			return modifyTime;
		}

		public void setModifyTime(Date modifyTime) {
			this.modifyTime = modifyTime;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getHeadPortrait() {
			return headPortrait;
		}

		public void setHeadPortrait(String headPortrait) {
			this.headPortrait = headPortrait;
		}
	}
