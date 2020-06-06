package com.open.custom.api.app.mapper;

import com.open.custom.api.app.model.OpenAppInfo;
import com.open.custom.api.app.model.OpenAppInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpenAppInfoMapper {
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
}