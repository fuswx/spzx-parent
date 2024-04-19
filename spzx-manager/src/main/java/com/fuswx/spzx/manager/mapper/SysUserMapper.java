package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.dto.system.SysUserDto;
import com.fuswx.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);

    //1、用户条件分页查询接口
    List<SysUser> findByPage(SysUserDto sysUserDto);

    //2、用户添加
    void save(SysUser sysUser);

    //3、用户修改
    void update(SysUser sysUser);

    //4、用户删除
    void delete(Integer userId);
}
