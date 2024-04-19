package com.fuswx.spzx.manager.controller;

import com.fuswx.spzx.manager.service.SysMenuService;
import com.fuswx.spzx.model.entity.system.SysMenu;
import com.fuswx.spzx.model.vo.common.Result;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/system/sysMenu")
@Tag(name = "系统菜单接口", description = "有关于系统菜单的接口")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    //菜单删除
    @DeleteMapping("/removeById/{id}")
    @Operation(summary = "菜单删除接口", description = "菜单删除接口")
    public Result removeById(@PathVariable("id") Integer id){
        sysMenuService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //菜单修改
    @PutMapping("/update")
    @Operation(summary = "菜单修改接口", description = "菜单修改接口")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //菜单添加
    @PostMapping("/save")
    @Operation(summary = "菜单添加接口", description = "菜单添加接口")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //菜单列表
    @GetMapping("/findNodes")
    @Operation(summary = "菜单列表接口", description = "菜单列表接口")
    public Result findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
