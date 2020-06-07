package com.open.custom.api.service.impl;

import com.open.custom.api.mapper.OpenUserInfoExtMapper;
import com.open.custom.api.model.OpenUserInfoExt;
import com.open.custom.api.model.OpenUserInfoExtExample;
import com.open.custom.api.model.OpenUserInfoExtWithBLOBs;
import com.open.custom.api.service.IOpenUserInfoExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("openUserInfoExtServiceImpl")
public class OpenUserInfoExtServiceImpl implements IOpenUserInfoExtService {

    @Autowired
    private OpenUserInfoExtMapper openUserInfoExtMapper;


    @Override
    public int countByExample(OpenUserInfoExtExample example) {
        return openUserInfoExtMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(OpenUserInfoExtExample example) {
        return openUserInfoExtMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return openUserInfoExtMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpenUserInfoExtWithBLOBs record) {
        return openUserInfoExtMapper.insert(record);
    }

    @Override
    public int insertSelective(OpenUserInfoExtWithBLOBs record) {
        return openUserInfoExtMapper.insertSelective(record);
    }

    @Override
    public List<OpenUserInfoExtWithBLOBs> selectByExampleWithBLOBs(OpenUserInfoExtExample example) {
        return openUserInfoExtMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public List<OpenUserInfoExt> selectByExample(OpenUserInfoExtExample example) {
        return openUserInfoExtMapper.selectByExample(example);
    }

    @Override
    public OpenUserInfoExtWithBLOBs selectByPrimaryKey(Integer id) {
        return openUserInfoExtMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(OpenUserInfoExtWithBLOBs record, OpenUserInfoExtExample example) {
        return openUserInfoExtMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExampleWithBLOBs(OpenUserInfoExtWithBLOBs record, OpenUserInfoExtExample example) {
        return openUserInfoExtMapper.updateByExampleWithBLOBs(record, example);
    }

    @Override
    public int updateByExample(OpenUserInfoExt record, OpenUserInfoExtExample example) {
        return openUserInfoExtMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(OpenUserInfoExtWithBLOBs record) {
        return openUserInfoExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(OpenUserInfoExtWithBLOBs record) {
        return openUserInfoExtMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(OpenUserInfoExt record) {
        return openUserInfoExtMapper.updateByPrimaryKey(record);
    }

    @Override
    public OpenUserInfoExt getUserExtInfoByCode(String userCode) {
        OpenUserInfoExtExample example = new OpenUserInfoExtExample();
        OpenUserInfoExtExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andUserCodeEqualTo(userCode);
        List<OpenUserInfoExt> openUserInfoExts = openUserInfoExtMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(openUserInfoExts)) {
            return null;
        }
        return openUserInfoExts.get(0);
    }

    @Override
    public OpenUserInfoExtWithBLOBs getUserExtInfoWithBLOBsByCode(String userCode) {
        OpenUserInfoExtExample example = new OpenUserInfoExtExample();
        OpenUserInfoExtExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andUserCodeEqualTo(userCode);
        List<OpenUserInfoExtWithBLOBs> openUserInfoExts = openUserInfoExtMapper.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isEmpty(openUserInfoExts)) {
            return null;
        }
        return openUserInfoExts.get(0);
    }


}
