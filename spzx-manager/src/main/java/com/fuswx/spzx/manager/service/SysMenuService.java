package com.fuswx.spzx.manager.service;

import com.fuswx.spzx.model.entity.system.SysMenu;
import com.fuswx.spzx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {

    //菜单列表
    List<SysMenu> findNodes();

    //菜单添加
    void save(SysMenu sysMenu);

    //菜单修改
    void update(SysMenu sysMenu);

    //菜单删除
    void removeById(Integer id);

    //查询用户可以操作的菜单
    List<SysMenuVo> findMenusByUserId();
}
