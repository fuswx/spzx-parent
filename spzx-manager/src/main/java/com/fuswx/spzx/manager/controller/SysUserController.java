package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.SysUserService;
import com.fuswx.spzx.model.dto.system.AssginRoleDto;
import com.fuswx.spzx.model.dto.system.SysUserDto;
import com.fuswx.spzx.model.entity.system.SysUser;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    //1、用户条件分页查询接口
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable("pageNum") Integer pageNum,
                             @PathVariable("pageSize") Integer pageSize,
                             SysUserDto sysUserDto){
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(pageNum, pageSize, sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //2、用户添加
    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //3、用户修改
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //4、用户删除
    @DeleteMapping("/deleteById/{userId}")
    public Result deleteById(@PathVariable("userId") Integer userId){
        sysUserService.deleteById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //用户分配角色
    //保存分配数据
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto){
        sysUserService.doAssign(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
