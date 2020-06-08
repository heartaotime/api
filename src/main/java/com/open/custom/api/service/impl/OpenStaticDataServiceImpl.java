package com.open.custom.api.service.impl;

import com.open.custom.api.mapper.OpenStaticDataMapper;
import com.open.custom.api.model.OpenStaticData;
import com.open.custom.api.model.OpenStaticDataExample;
import com.open.custom.api.service.IOpenStaticDataService;
import com.open.custom.api.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service("openStaticDataServiceImpl")
public class OpenStaticDataServiceImpl implements IOpenStaticDataService {

    @Autowired
    private OpenStaticDataMapper openStaticDataMapper;

    @Autowired
    private RedisService redisService;

    @Value("${custCacheKey.OPEN_STATIC_DATA}")
    private String OPEN_STATIC_DATA;

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

    @Override
    public List<OpenStaticData> getStaticDataByCodeType(String codeType) {
        if (StringUtils.isEmpty(codeType)) {
            return null;
        }
        Object cacheData = redisService.hget(OPEN_STATIC_DATA, codeType);
        List<OpenStaticData> openStaticDatas = new ArrayList<>();
        if (cacheData == null) {
            OpenStaticDataExample example = new OpenStaticDataExample();
            OpenStaticDataExample.Criteria criteria = example.createCriteria();
            criteria.andCodeTypeEqualTo(codeType);
            criteria.andStateEqualTo(1);
            openStaticDatas = selectByExample(example);
            redisService.set(OPEN_STATIC_DATA, codeType);
        } else {
            openStaticDatas = (List<OpenStaticData>) cacheData;
        }
        return openStaticDatas;
    }
}
