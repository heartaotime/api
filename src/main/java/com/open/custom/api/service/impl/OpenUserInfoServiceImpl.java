package com.open.custom.api.service.impl;

import com.open.custom.api.mapper.OpenUserInfoMapper;
import com.open.custom.api.mapper.extend.OpenUserInfoExtendMapper;
import com.open.custom.api.model.OpenUserInfo;
import com.open.custom.api.model.OpenUserInfoExample;
import com.open.custom.api.service.IOpenUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("openUserInfoServiceImpl")
public class OpenUserInfoServiceImpl implements IOpenUserInfoService {

    @Autowired
    private OpenUserInfoMapper openUserInfoMapper;

    @Autowired
    private OpenUserInfoExtendMapper openUserInfoExtendMapper;


    @Override
    public int countByExample(OpenUserInfoExample example) {
        return openUserInfoMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(OpenUserInfoExample example) {
        return openUserInfoMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return openUserInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpenUserInfo record) {
        return openUserInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(OpenUserInfo record) {
        return openUserInfoMapper.insertSelective(record);
    }

    @Override
    public List<OpenUserInfo> selectByExample(OpenUserInfoExample example) {
        return openUserInfoMapper.selectByExample(example);
    }

    @Override
    public OpenUserInfo selectByPrimaryKey(Integer id) {
        return openUserInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(OpenUserInfo record, OpenUserInfoExample example) {
        return openUserInfoMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(OpenUserInfo record, OpenUserInfoExample example) {
        return openUserInfoMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(OpenUserInfo record) {
        return openUserInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpenUserInfo record) {
        return openUserInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer checkUserExist(String appCode, String account) {
        return openUserInfoExtendMapper.checkUserExist(appCode, account);
    }

    @Override
    public OpenUserInfo login(String appCode, String account) {
        return openUserInfoExtendMapper.login(appCode, account);
    }

    @Override
    public OpenUserInfo getUserByCode(String userCode) {
        return openUserInfoExtendMapper.getUserByCode(userCode);
    }
}
