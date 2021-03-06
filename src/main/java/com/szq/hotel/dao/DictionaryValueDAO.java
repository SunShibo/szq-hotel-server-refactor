package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.DictionaryValueBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//字典 对应的 字典值
public interface DictionaryValueDAO {

    //增
    Integer addDic(DictionaryValueBO dictionaryBO);
    //删
    Integer delDic(@Param("idArr") Integer[] idArr);

    //改
    Integer updDic(DictionaryValueBO dictionaryBO);
    //查
    List<DictionaryValueBO> getDIcs(@Param("kid") Integer kid);

    Integer getIdByValue(String value);
}
