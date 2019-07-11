package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.DictionaryKeyBO;

import java.util.List;

//字典
public interface DictionaryDAO {

    //查询
    List<DictionaryKeyBO> getDictionaryBOs();
}
