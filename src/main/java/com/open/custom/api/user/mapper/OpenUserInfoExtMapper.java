package com.open.custom.api.user.mapper;

import com.open.custom.api.user.model.OpenUserInfoExt;
import com.open.custom.api.user.model.OpenUserInfoExtExample;
import com.open.custom.api.user.model.OpenUserInfoExtWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpenUserInfoExtMapper {
    int countByExample(OpenUserInfoExtExample example);

    int deleteByExample(OpenUserInfoExtExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpenUserInfoExtWithBLOBs record);

    int insertSelective(OpenUserInfoExtWithBLOBs record);

    List<OpenUserInfoExtWithBLOBs> selectByExampleWithBLOBs(OpenUserInfoExtExample example);

    List<OpenUserInfoExt> selectByExample(OpenUserInfoExtExample example);

    OpenUserInfoExtWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpenUserInfoExtWithBLOBs record, @Param("example") OpenUserInfoExtExample example);

    int updateByExampleWithBLOBs(@Param("record") OpenUserInfoExtWithBLOBs record, @Param("example") OpenUserInfoExtExample example);

    int updateByExample(@Param("record") OpenUserInfoExt record, @Param("example") OpenUserInfoExtExample example);

    int updateByPrimaryKeySelective(OpenUserInfoExtWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(OpenUserInfoExtWithBLOBs record);

    int updateByPrimaryKey(OpenUserInfoExt record);
}