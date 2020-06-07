package com.open.custom.api.service.impl;

import com.open.custom.api.mapper.OpenStaticDataMapper;
import com.open.custom.api.model.OpenStaticData;
import com.open.custom.api.model.OpenStaticDataExample;
import com.open.custom.api.service.IOpenStaticDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("openStaticDataServiceImpl")
public class OpenStaticDataServiceImpl implements IOpenStaticDataService {

    @Autowired
    private OpenStaticDataMapper openStaticDataMapper;


    @Override
    public int countByExample(OpenStaticDataExample example) {
        return openStaticDataMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(OpenStaticDataExample example) {
        return openStaticDataMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return openStaticDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpenStaticData record) {
        return openStaticDataMapper.insert(record);
    }

    @Override
    public int insertSelective(OpenStaticData record) {
        return openStaticDataMapper.insertSelective(record);
    }

    @Override
    public List<OpenStaticData> selectByExampleWithBLOBs(OpenStaticDataExample example) {
        return openStaticDataMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public List<OpenStaticData> selectByExample(OpenStaticDataExample example) {
        return openStaticDataMapper.selectByExample(example);
    }

    @Override
    public OpenStaticData selectByPrimaryKey(Integer id) {
        return openStaticDataMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(OpenStaticData record, OpenStaticDataExample example) {
        return openStaticDataMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExampleWithBLOBs(OpenStaticData record, OpenStaticDataExample example) {
        return openStaticDataMapper.updateByExampleWithBLOBs(record, example);
    }

    @Override
    public int updateByExample(OpenStaticData record, OpenStaticDataExample example) {
        return openStaticDataMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(OpenStaticData record) {
        return openStaticDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(OpenStaticData record) {
        return openStaticDataMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(OpenStaticData record) {
        return openStaticDataMapper.updateByPrimaryKey(record);
    }
}
