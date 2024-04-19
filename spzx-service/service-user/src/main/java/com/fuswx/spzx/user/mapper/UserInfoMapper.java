package com.fuswx.spzx.user.mapper;

import com.fuswx.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    //校验用户名不能重复
    UserInfo selectByUserName(String username);

    //注册
    void save(UserInfo userInfo);
}
