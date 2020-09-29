package com.open.custom.api.service;

import com.open.custom.api.model.YsCustUserInfo;
import com.open.custom.api.model.YsCustUserInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IYsCustUserInfoService {
    int countByExample(YsCustUserInfoExample example);

    int deleteByExample(YsCustUserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YsCustUserInfo record);

    int insertSelective(YsCustUserInfo record);

    List<YsCustUserInfo> selectByExample(YsCustUserInfoExample example);

    YsCustUserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YsCustUserInfo record, @Param("example") YsCustUserInfoExample example);

    int updateByExample(@Param("record") YsCustUserInfo record, @Param("example") YsCustUserInfoExample example);

    int updateByPrimaryKeySelective(YsCustUserInfo record);

    int updateByPrimaryKey(YsCustUserInfo record);
}