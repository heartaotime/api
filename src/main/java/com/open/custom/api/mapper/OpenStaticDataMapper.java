package com.open.custom.api.mapper;

import com.open.custom.api.model.OpenStaticData;
import com.open.custom.api.model.OpenStaticDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpenStaticDataMapper {
    int countByExample(OpenStaticDataExample example);

    int deleteByExample(OpenStaticDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpenStaticData record);

    int insertSelective(OpenStaticData record);

    List<OpenStaticData> selectByExampleWithBLOBs(OpenStaticDataExample example);

    List<OpenStaticData> selectByExample(OpenStaticDataExample example);

    OpenStaticData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpenStaticData record, @Param("example") OpenStaticDataExample example);

    int updateByExampleWithBLOBs(@Param("record") OpenStaticData record, @Param("example") OpenStaticDataExample example);

    int updateByExample(@Param("record") OpenStaticData record, @Param("example") OpenStaticDataExample example);

    int updateByPrimaryKeySelective(OpenStaticData record);

    int updateByPrimaryKeyWithBLOBs(OpenStaticData record);

    int updateByPrimaryKey(OpenStaticData record);
}