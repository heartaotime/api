package com.open.custom.api.service.impl;

import com.open.custom.api.mapper.OpenApiAccessMapper;
import com.open.custom.api.model.OpenApiAccess;
import com.open.custom.api.model.OpenApiAccessExample;
import com.open.custom.api.model.OpenApiAccessWithBLOBs;
import com.open.custom.api.service.IOpenApiAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("openApiAccessServiceImpl")
public class OpenApiAccessServiceImpl implements IOpenApiAccessService {

    @Autowired
    private OpenApiAccessMapper openApiAccessMapper;


    @Override
    public int countByExample(OpenApiAccessExample example) {
        return openApiAccessMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(OpenApiAccessExample example) {
        return openApiAccessMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return openApiAccessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpenApiAccessWithBLOBs record) {
        return openApiAccessMapper.insert(record);
    }

    @Override
    public int insertSelective(OpenApiAccessWithBLOBs record) {
        return openApiAccessMapper.insertSelective(record);
    }

    @Override
    public List<OpenApiAccessWithBLOBs> selectByExampleWithBLOBs(OpenApiAccessExample example) {
        return openApiAccessMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public List<OpenApiAccess> selectByExample(OpenApiAccessExample example) {
        return openApiAccessMapper.selectByExample(example);
    }

    @Override
    public OpenApiAccessWithBLOBs selectByPrimaryKey(Integer id) {
        return openApiAccessMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(OpenApiAccessWithBLOBs record, OpenApiAccessExample example) {
        return openApiAccessMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExampleWithBLOBs(OpenApiAccessWithBLOBs record, OpenApiAccessExample example) {
        return openApiAccessMapper.updateByExampleWithBLOBs(record, example);
    }

    @Override
    public int updateByExample(OpenApiAccess record, OpenApiAccessExample example) {
        return openApiAccessMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(OpenApiAccessWithBLOBs record) {
        return openApiAccessMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(OpenApiAccessWithBLOBs record) {
        return openApiAccessMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(OpenApiAccess record) {
        return openApiAccessMapper.updateByPrimaryKey(record);
    }
}
