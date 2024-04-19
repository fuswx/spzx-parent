package com.fuswx.spzx.manager.controller;


import com.fuswx.spzx.manager.service.SysRoleMenuService;
import com.fuswx.spzx.model.dto.system.AssginMenuDto;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
@Tag(name = "系统角色菜单接口", description = "有关于角色菜单的接口")
public class SysRoleMenuController {

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    //1、查询所有菜单 和 查询角色分配过菜单id列表
    @GetMapping(value = "/findSysRoleMenuByRoleId/{roleId}")
    @Operation(summary = "查询所有菜单 和 查询角色分配过菜单id列表接口", description = "查询所有菜单 和 查询角色分配过菜单id列表接口")
    public Result findSysRoleMenuByRoleId(@PathVariable(value = "roleId") Integer roleId){
        Map<String, Object> sysRoleMenuList = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(sysRoleMenuList, ResultCodeEnum.SUCCESS);
    }

    //2、保存角色分配菜单数据
    @PostMapping("/doAssign")
    @Operation(summary = "保存角色分配菜单数据接口", description = "保存角色分配菜单数据接口")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto){
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
