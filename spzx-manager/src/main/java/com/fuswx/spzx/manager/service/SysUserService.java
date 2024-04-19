package com.fuswx.spzx.manager.service;

import com.fuswx.spzx.model.dto.system.AssginRoleDto;
import com.fuswx.spzx.model.dto.system.LoginDto;
import com.fuswx.spzx.model.dto.system.SysUserDto;
import com.fuswx.spzx.model.entity.system.SysUser;
import com.fuswx.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    //1、用户条件分页查询接口
    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    //2、用户添加
    void saveSysUser(SysUser sysUser);

    //3、用户修改
    void updateSysUser(SysUser sysUser);

    void deleteById(Integer userId);

    //用户分配角色
    void doAssign(AssginRoleDto assginRoleDto);
}
