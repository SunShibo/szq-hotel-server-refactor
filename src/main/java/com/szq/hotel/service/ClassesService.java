package com.szq.hotel.service;

import com.szq.hotel.dao.ClassesDAO;
import com.szq.hotel.entity.bo.ClassesBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("ClassesService")
@Transactional
public class ClassesService {
    private static final Logger log = LoggerFactory.getLogger(ClassesService.class);
    @Resource
    private ClassesDAO classesDAO;

    /**
     * 查询班次
     * @return
     */
    public List<ClassesBO> queryClasses(Integer hotelId){
        return  classesDAO.queryClasses(hotelId);
    }

    /**
     * 添加班次
     */
    public void addClasses(ClassesBO classesBO,Integer userId){
        log.info("start addClasses..........................");
        classesBO.setCreateUserId(userId);
        log.info("param{}",classesBO);
        classesDAO.addClasses(classesBO);
        log.info("end addClasses..........................");
    }

    /**
     * 修改班次
     */
    public void updateClasses(ClassesBO classesBO,Integer userId){
        classesBO.setUpdateUserId(userId);
        classesDAO.updateClasses(classesBO);
    }

    /**
     *  删除班次
     */
    public void deleteClasses(Integer id){
        classesDAO.deleteClasses(id);
    }
}
