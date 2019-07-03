package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

public class HouseTypeBO extends BaseModel {
    private Integer id;

    private String name;

    private Double timePrice;

    private Double typePrice;

    private Integer hotelId;

    private Integer houseTypeMainId;

    private Integer level;

    private String area;

    private String floor;

    private String smokingPolicy;

    private String window;

    private String tenantable;

    private String bedType;

    private String wifi;

    private String meals;

    private String bath;

    private String expensePolicy;

    private String amenities;

    private String mediaTechnology;

    private String foodDiet;

    private Integer filtratebedtype;

    private Integer breakfastType;

    private Integer broadband;

    private Integer payState;

    private Integer salesType;

    private Integer ifCancel;

    private String policyService;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getTimePrice() {
        return timePrice;
    }

    public void setTimePrice(Double timePrice) {
        this.timePrice = timePrice;
    }

    public Double getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(Double typePrice) {
        this.typePrice = typePrice;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getHouseTypeMainId() {
        return houseTypeMainId;
    }

    public void setHouseTypeMainId(Integer houseTypeMainId) {
        this.houseTypeMainId = houseTypeMainId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor == null ? null : floor.trim();
    }

    public String getSmokingPolicy() {
        return smokingPolicy;
    }

    public void setSmokingPolicy(String smokingPolicy) {
        this.smokingPolicy = smokingPolicy == null ? null : smokingPolicy.trim();
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window == null ? null : window.trim();
    }

    public String getTenantable() {
        return tenantable;
    }

    public void setTenantable(String tenantable) {
        this.tenantable = tenantable == null ? null : tenantable.trim();
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType == null ? null : bedType.trim();
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi == null ? null : wifi.trim();
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals == null ? null : meals.trim();
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath == null ? null : bath.trim();
    }

    public String getExpensePolicy() {
        return expensePolicy;
    }

    public void setExpensePolicy(String expensePolicy) {
        this.expensePolicy = expensePolicy == null ? null : expensePolicy.trim();
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities == null ? null : amenities.trim();
    }

    public String getMediaTechnology() {
        return mediaTechnology;
    }

    public void setMediaTechnology(String mediaTechnology) {
        this.mediaTechnology = mediaTechnology == null ? null : mediaTechnology.trim();
    }

    public String getFoodDiet() {
        return foodDiet;
    }

    public void setFoodDiet(String foodDiet) {
        this.foodDiet = foodDiet == null ? null : foodDiet.trim();
    }

    public Integer getFiltratebedtype() {
        return filtratebedtype;
    }

    public void setFiltratebedtype(Integer filtratebedtype) {
        this.filtratebedtype = filtratebedtype;
    }

    public Integer getBreakfastType() {
        return breakfastType;
    }

    public void setBreakfastType(Integer breakfastType) {
        this.breakfastType = breakfastType;
    }

    public Integer getBroadband() {
        return broadband;
    }

    public void setBroadband(Integer broadband) {
        this.broadband = broadband;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public Integer getSalesType() {
        return salesType;
    }

    public void setSalesType(Integer salesType) {
        this.salesType = salesType;
    }

    public Integer getIfCancel() {
        return ifCancel;
    }

    public void setIfCancel(Integer ifCancel) {
        this.ifCancel = ifCancel;
    }

    public String getPolicyService() {
        return policyService;
    }

    public void setPolicyService(String policyService) {
        this.policyService = policyService == null ? null : policyService.trim();
    }
}