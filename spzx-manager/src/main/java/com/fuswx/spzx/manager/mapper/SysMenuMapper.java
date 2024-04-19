package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    //1、查询所有菜单，返回lis集合
    List<SysMenu> findAll();

    //菜单添加
    void save(SysMenu sysMenu);

    //菜单修改
    void updateById(SysMenu sysMenu);

    //根据当前菜单id，查询是否包含子菜单
    Integer selectCountById(Integer id);

    //count等于0，直接删除count等于0，直接删除
    void delete(Integer id);

    //根据userId查询可以操作的菜单
    List<SysMenu> findMenusByUserId(Integer userId);

    //获取当前添加菜单的父菜单
    SysMenu selectParentMenu(Long parentId);
}
