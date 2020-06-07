package com.open.custom.api.service;

import com.open.custom.api.model.OpenUserInfo;
import com.open.custom.api.model.OpenUserInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOpenUserInfoService {
    int countByExample(OpenUserInfoExample example);

    int deleteByExample(OpenUserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpenUserInfo record);

    int insertSelective(OpenUserInfo record);

    List<OpenUserInfo> selectByExample(OpenUserInfoExample example);

    OpenUserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpenUserInfo record, @Param("example") OpenUserInfoExample example);

    int updateByExample(@Param("record") OpenUserInfo record, @Param("example") OpenUserInfoExample example);

    int updateByPrimaryKeySelective(OpenUserInfo record);

    int updateByPrimaryKey(OpenUserInfo record);


    Integer checkUserExist(String appCode, String account);

    OpenUserInfo login(String appCode, String account);

    OpenUserInfo getUserByCode(String userCode);

}