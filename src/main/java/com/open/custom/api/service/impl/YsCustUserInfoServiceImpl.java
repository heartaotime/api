package com.open.custom.api.service.impl;


import com.open.custom.api.mapper.YsCustUserInfoMapper;
import com.open.custom.api.model.YsCustUserInfo;
import com.open.custom.api.model.YsCustUserInfoExample;
import com.open.custom.api.service.IYsCustUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ysCustUserInfoServiceImpl")
public class YsCustUserInfoServiceImpl implements IYsCustUserInfoService {

    @Autowired
    private YsCustUserInfoMapper ysCustUserInfoMapper;


    @Override
    public int countByExample(YsCustUserInfoExample example) {
        return ysCustUserInfoMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(YsCustUserInfoExample example) {
        return ysCustUserInfoMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return ysCustUserInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(YsCustUserInfo record) {
        return ysCustUserInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(YsCustUserInfo record) {
        return ysCustUserInfoMapper.insertSelective(record);
    }

    @Override
    public List<YsCustUserInfo> selectByExample(YsCustUserInfoExample example) {
        return ysCustUserInfoMapper.selectByExample(example);
    }

    @Override
    public YsCustUserInfo selectByPrimaryKey(Integer id) {
        return ysCustUserInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(YsCustUserInfo record, YsCustUserInfoExample example) {
        return ysCustUserInfoMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(YsCustUserInfo record, YsCustUserInfoExample example) {
        return ysCustUserInfoMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(YsCustUserInfo record) {
        return ysCustUserInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(YsCustUserInfo record) {
        return ysCustUserInfoMapper.updateByPrimaryKey(record);
    }
}
