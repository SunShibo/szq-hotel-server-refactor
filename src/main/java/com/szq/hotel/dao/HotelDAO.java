package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.CommonBO;
import com.szq.hotel.entity.bo.HotelBO;

import java.util.List;

public interface HotelDAO {
    /**
     * 添加酒店
     */
    void addHotel(HotelBO  hotelBO);

    /**
     * 修改酒店
     */
    void updateHotel(HotelBO hotelBO);


    /**
     *  查询酒店
     */
     List<HotelBO>  queryHotel();

    /**
     * 通过id查询酒店
     */
    HotelBO  queryHotelById(Integer hotelId);

    /**
     * 查询登录酒店
     */
    List<CommonBO>  queryLoginHotel();
}
