package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.SysRoleService;
import com.fuswx.spzx.model.dto.system.SysRoleDto;
import com.fuswx.spzx.model.entity.system.SysRole;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    //查询所有角色
    @GetMapping("/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable("userId")Integer userId){
        Map<String, Object> map = sysRoleService.findAll(userId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    //4、角色删除的方法
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId") Long roleId){
        sysRoleService.deleteById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //3、角色修改的方法
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //2、角色添加的方法
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //1、角色列表的方法
    //current：代表当前页 limit：代表每页显示记录数
    //SysRoleDto：条件角色名称对象
    @PostMapping("/findByPage/{current}/{limit}")
    public Result<PageInfo<SysRole>> findByPage(@PathVariable(value = "current") Integer current, @PathVariable(value = "limit") Integer limit, @RequestBody SysRoleDto sysRoleDto){
        //pageHelper插件实现分页
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
