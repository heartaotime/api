package com.open.custom.api.service;

import com.github.pagehelper.PageInfo;
import com.open.custom.api.model.OpenStaticData;
import com.open.custom.api.model.OpenStaticDataExample;
import org.apache.ibatis.annotations.Param;

import java.util.Comparator;
import java.util.List;

public interface IOpenStaticDataService {
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

    List<OpenStaticData> getStaticDataByCodeType(String codeType);
    List<OpenStaticData> getStaticDataByCodeType(String codeType, Comparator comparator);

    PageInfo<OpenStaticData> getStaticDataByCodeType(String codeType, int pageNum, int pageSize);

    PageInfo<OpenStaticData> getStaticDataByCodeType(String codeType, int pageNum, int pageSize, Comparator comparator);

    void saveData2Redis();

}