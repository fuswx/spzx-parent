package com.fuswx.spzx.manager.mapper;

import com.fuswx.spzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    //查询角色分配过菜单id列表
    List<Integer> findSysRoleMenuByRoleId(Integer roleId);

    //删除角色分配菜单数据
    void deleteByRoleId(Integer roleId);

    //保存分配数据
    void doAssign(AssginMenuDto assginMenuDto);

    //将该id的菜单设置为开
    void updateSysRoleMenuIsHalf(Integer menuId);
}
