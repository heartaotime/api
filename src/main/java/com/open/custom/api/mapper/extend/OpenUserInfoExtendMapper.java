package com.open.custom.api.mapper.extend;

import com.open.custom.api.model.OpenUserInfo;

public interface OpenUserInfoExtendMapper {

    Integer checkUserExist(String appCode, String account);

    OpenUserInfo login(String appCode, String account);

    OpenUserInfo getUserByCode(String userCode);

}
