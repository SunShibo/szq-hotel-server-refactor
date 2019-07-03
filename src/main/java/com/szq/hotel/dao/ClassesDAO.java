package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.ClassesBO;

import java.util.List;

public interface ClassesDAO {

    //查询所有班次
    List<ClassesBO> getAllClassesBO();
}
