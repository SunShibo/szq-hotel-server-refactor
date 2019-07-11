package com.szq.hotel.service;

import com.szq.hotel.dao.DictionaryDAO;
import com.szq.hotel.entity.bo.DictionaryKeyBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service("DictionaryService")
public class DictionaryService {

    @Resource
    DictionaryDAO dictionaryDAO;

    //查询字典
    public List<DictionaryKeyBO> getDictionaryBOs(){
        return dictionaryDAO.getDictionaryBOs();
    }

}
