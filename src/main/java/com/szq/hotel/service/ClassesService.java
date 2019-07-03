package com.szq.hotel.service;

import com.szq.hotel.dao.ClassesDAO;
import com.szq.hotel.dao.TestDAO;
import com.szq.hotel.entity.bo.ClassesBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */
@Service("ClassesService")
@Transactional
public class ClassesService {
    @Resource
    private ClassesDAO classesDAO;

    //查询所有班次
    public List<ClassesBO> getAllClassesBO(){
        return classesDAO.getAllClassesBO();
    }

}
