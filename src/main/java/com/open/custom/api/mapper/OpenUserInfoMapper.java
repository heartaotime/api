package com.open.custom.api.mapper;

import com.open.custom.api.model.OpenUserInfo;
import com.open.custom.api.model.OpenUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpenUserInfoMapper {
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
}