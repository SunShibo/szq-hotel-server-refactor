package com.szq.hotel.service;

import com.szq.hotel.dao.HouseTypeDAO;
import com.szq.hotel.entity.bo.HouseTypeBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Bin Wang
 * @date: Created in 13:19 2019/7/3
 */
@Service
@Transactional
public class HouseTypeService {

    @Resource
    private HouseTypeDAO houseTypeDAO;

    public List<HouseTypeBO> queryHouseTypeList(Integer id){
        return houseTypeDAO.queryHouseTypeList(id);
    }

    public int insertSelective(HouseTypeBO record){
        return houseTypeDAO.insertSelective(record);
    }

    public int updateByPrimaryKeySelective(HouseTypeBO record) {
        return houseTypeDAO.updateByPrimaryKeySelective(record);
    }

    public int delete(Integer id){
        return houseTypeDAO.updateLevel(id);
    }
}
