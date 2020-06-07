package com.open.custom.api.service.impl;

import com.open.custom.api.mapper.OpenAppInfoMapper;
import com.open.custom.api.mapper.extend.OpenAppInfoExtendMapper;
import com.open.custom.api.model.OpenAppInfo;
import com.open.custom.api.model.OpenAppInfoExample;
import com.open.custom.api.service.IOpenAppInfoService;
import com.open.custom.api.exception.BusiException;
import com.open.custom.api.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service("openAppInfoServiceImpl")
public class OpenAppInfoServiceImpl implements IOpenAppInfoService {

    @Autowired
    private OpenAppInfoMapper openAppInfoMapper;

    @Autowired
    private OpenAppInfoExtendMapper openAppInfoExtendMapper;

    @Autowired
    private RedisService redisService;

    @Value("${custCacheKey.OPEN_APP_INFO}")
    private String OPEN_APP_INFO;

    @Override
    public int countByExample(OpenAppInfoExample example) {
        return openAppInfoMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(OpenAppInfoExample example) {
        return openAppInfoMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return openAppInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpenAppInfo record) {
        return openAppInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(OpenAppInfo record) {
        return openAppInfoMapper.insertSelective(record);
    }

    @Override
    public List<OpenAppInfo> selectByExample(OpenAppInfoExample example) {
        return openAppInfoMapper.selectByExample(example);
    }

    @Override
    public OpenAppInfo selectByPrimaryKey(Integer id) {
        return openAppInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(OpenAppInfo record, OpenAppInfoExample example) {
        return openAppInfoMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(OpenAppInfo record, OpenAppInfoExample example) {
        return openAppInfoMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(OpenAppInfo record) {
        return openAppInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpenAppInfo record) {
        return openAppInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer checkAppExist(String appCode) {
        return openAppInfoExtendMapper.checkAppExist(appCode);
    }

    @Override
    public OpenAppInfo getAppInfoByCode(String appCode) {
        Object cacheData = redisService.get(OPEN_APP_INFO);
        List<OpenAppInfo> openAppInfos = new ArrayList<>();
        if (cacheData == null) {
            OpenAppInfoExample example = new OpenAppInfoExample();
            OpenAppInfoExample.Criteria criteria = example.createCriteria();
            criteria.andStateEqualTo(1);
            openAppInfos = selectByExample(example);
            redisService.set(OPEN_APP_INFO, openAppInfos);
        } else {
            openAppInfos = (List<OpenAppInfo>) cacheData;
        }

        if (CollectionUtils.isEmpty(openAppInfos)) {
            return null;
        }

        for (OpenAppInfo openAppInfo : openAppInfos) {
            String appCodeQ = openAppInfo.getAppCode();
            if (appCode.equals(appCodeQ)) {
                return openAppInfo;
            }
        }

        return null;
    }

    @Override
    public OpenAppInfo assertAppCode(String appCode) throws BusiException {
        OpenAppInfo openAppInfo = getAppInfoByCode(appCode);
        if (openAppInfo == null) {
            throw new BusiException("appCode不存在，请联系 [ heartaotime@foxmail.com ] 获取独立的应用CODE");
        }
        return openAppInfo;
    }
}
