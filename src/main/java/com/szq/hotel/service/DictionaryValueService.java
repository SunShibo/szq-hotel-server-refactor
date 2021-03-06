package com.szq.hotel.service;

import com.szq.hotel.dao.DictionaryValueDAO;
import com.szq.hotel.entity.bo.DictionaryValueBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

//字典 对应的 字典值
@Transactional
@Service("DictionaryValueService")
public class DictionaryValueService {

    @Resource
    DictionaryValueDAO dictionaryValueDAO;
    //增
    public Integer addDic(DictionaryValueBO dictionaryBO){
        return dictionaryValueDAO.addDic(dictionaryBO);
    }
    //删
    public Integer delDic(Integer[] idArr){
        return dictionaryValueDAO.delDic(idArr);
    }

    //改
    public Integer updDic(DictionaryValueBO dictionaryBO){
        return dictionaryValueDAO.updDic(dictionaryBO);
    }
    //查
    public List<DictionaryValueBO> getDIcs(Integer kid){
        return dictionaryValueDAO.getDIcs(kid);
    }
    public Integer getIdByValue(String value){
        return dictionaryValueDAO.getIdByValue(value);
    }
}
