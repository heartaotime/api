package com.open.custom.api.user.sevice;

import com.open.custom.api.user.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOpenUserInfoExtService {
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

    OpenUserInfoExt getUserExtInfoByCode(String userCode);
    OpenUserInfoExtWithBLOBs getUserExtInfoWithBLOBsByCode(String userCode);

}