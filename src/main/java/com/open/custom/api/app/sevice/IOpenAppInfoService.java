package com.open.custom.api.app.sevice;

import com.open.custom.api.app.model.OpenAppInfo;
import com.open.custom.api.app.model.OpenAppInfoExample;
import com.open.custom.api.exception.BusiException;
import com.open.custom.api.user.model.OpenUserInfo;
import com.open.custom.api.user.model.OpenUserInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOpenAppInfoService {
    int countByExample(OpenAppInfoExample example);

    int deleteByExample(OpenAppInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpenAppInfo record);

    int insertSelective(OpenAppInfo record);

    List<OpenAppInfo> selectByExample(OpenAppInfoExample example);

    OpenAppInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpenAppInfo record, @Param("example") OpenAppInfoExample example);

    int updateByExample(@Param("record") OpenAppInfo record, @Param("example") OpenAppInfoExample example);

    int updateByPrimaryKeySelective(OpenAppInfo record);

    int updateByPrimaryKey(OpenAppInfo record);

    Integer checkAppExist(String appCode);


    OpenAppInfo getAppInfoByCode(String appCode);

    OpenAppInfo assertAppCode(String appCode) throws BusiException;

}