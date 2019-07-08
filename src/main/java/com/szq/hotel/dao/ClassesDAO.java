package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.ClassesBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassesDAO {

    /**
     * 查询班次
     * @return
     */
    List<ClassesBO> queryClasses(@Param("hotelId")Integer hotelId);

    /**
     * 添加班次
     */
    void addClasses(ClassesBO classesBO);

    /**
     * 修改班次
     */
    void updateClasses(ClassesBO classesBO);

    /**
     *  删除班次
     */
    void deleteClasses(Integer id);


}