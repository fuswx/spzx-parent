package com.fuswx.spzx.manager.service.impl;

import com.fuswx.spzx.manager.mapper.SysRoleMenuMapper;
import com.fuswx.spzx.manager.service.SysMenuService;
import com.fuswx.spzx.manager.service.SysRoleMenuService;
import com.fuswx.spzx.model.dto.system.AssginMenuDto;
import com.fuswx.spzx.model.entity.system.SysMenu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private SysMenuService sysMenuService;

    //1、查询所有菜单 和 查询角色分配过菜单id列表
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Integer roleId) {
        //查询所有菜单
        List<SysMenu> sysMenuList = sysMenuService.findNodes();

        //查询角色分配过菜单id列表
        List<Integer> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);

        Map<String, Object> map = new HashMap<>();
        map.put("sysMenuList", sysMenuList);
        map.put("roleMenuIds", roleMenuIds);
        return map;
    }

    //分配菜单
    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        //删除角色之前分配过的菜单数据
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());

        //保存分配的数据
        List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
        if (menuIdList != null && !menuIdList.isEmpty()) { //角色分配了菜单
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }
    }
}
