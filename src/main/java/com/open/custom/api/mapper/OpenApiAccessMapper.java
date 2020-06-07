package com.open.custom.api.mapper;

import com.open.custom.api.model.OpenApiAccess;
import com.open.custom.api.model.OpenApiAccessExample;
import com.open.custom.api.model.OpenApiAccessWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpenApiAccessMapper {
    int countByExample(OpenApiAccessExample example);

    int deleteByExample(OpenApiAccessExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpenApiAccessWithBLOBs record);

    int insertSelective(OpenApiAccessWithBLOBs record);

    List<OpenApiAccessWithBLOBs> selectByExampleWithBLOBs(OpenApiAccessExample example);

    List<OpenApiAccess> selectByExample(OpenApiAccessExample example);

    OpenApiAccessWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpenApiAccessWithBLOBs record, @Param("example") OpenApiAccessExample example);

    int updateByExampleWithBLOBs(@Param("record") OpenApiAccessWithBLOBs record, @Param("example") OpenApiAccessExample example);

    int updateByExample(@Param("record") OpenApiAccess record, @Param("example") OpenApiAccessExample example);

    int updateByPrimaryKeySelective(OpenApiAccessWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(OpenApiAccessWithBLOBs record);

    int updateByPrimaryKey(OpenApiAccess record);
}