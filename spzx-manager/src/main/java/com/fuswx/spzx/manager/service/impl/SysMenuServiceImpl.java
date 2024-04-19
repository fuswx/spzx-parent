package com.fuswx.spzx.manager.service.impl;

import com.fuswx.spzx.common.exception.FuswxException;
import com.fuswx.spzx.manager.mapper.SysMenuMapper;
import com.fuswx.spzx.manager.mapper.SysRoleMenuMapper;
import com.fuswx.spzx.manager.service.SysMenuService;
import com.fuswx.spzx.manager.utils.MenuHelper;
import com.fuswx.spzx.model.entity.system.SysMenu;
import com.fuswx.spzx.model.entity.system.SysUser;
import com.fuswx.spzx.model.vo.common.ResultCodeEnum;
import com.fuswx.spzx.model.vo.system.SysMenuVo;
import com.fuswx.spzx.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    //菜单列表
    @Override
    public List<SysMenu> findNodes() {
        //1、查询所有菜单，返回lis集合
        List<SysMenu> sysMenuList = sysMenuMapper.findAll();
        if (CollectionUtils.isEmpty(sysMenuList)){
            return null;
        }

        //2、调用工具类的方法，把返回lis集合封装要求的数据格式
        List<SysMenu> buildTree = MenuHelper.buildTree(sysMenuList);
        return buildTree;
    }

    //菜单添加
    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);

        //新添加子菜单，把父菜单isHalf半开状态设置为1
        updateSysRoleMenuIsHalf(sysMenu);
    }

    //新添加子菜单，把父菜单isHalf半开状态设置为1
    private void updateSysRoleMenuIsHalf(SysMenu sysMenu){
        //获取当前添加菜单的父菜单
        SysMenu parentMenu = sysMenuMapper.selectParentMenu(sysMenu.getParentId());

        if (parentMenu != null){
            //将该id的菜单设置为开
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
            //递归调用
            updateSysRoleMenuIsHalf(parentMenu);
        }
    }

    //菜单修改
    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu);
    }

    //菜单删除
    @Override
    public void removeById(Integer id) {
        //根据当前菜单id，查询是否包含子菜单
        Integer count = sysMenuMapper.selectCountById(id);

        //判断，count>0，包含子菜单
        if (count > 0) {
            throw new FuswxException(ResultCodeEnum.NODE_ERROR);
        }

        //count等于0，直接删除
        sysMenuMapper.delete(id);
    }

    //查询用户可以操作的菜单
    @Override
    public List<SysMenuVo> findMenusByUserId() {
        //获取当前登录的用户id
        SysUser sysUser = AuthContextUtil.get();
        Integer userId = sysUser.getId();

        //根据useId查询可以操作的菜单
        List<SysMenu> syMenuList = sysMenuMapper.findMenusByUserId(userId);

        //封装要求数据格式，并返回
        List<SysMenu> sysMenuList = MenuHelper.buildTree(syMenuList);
        return this.buildMenus(sysMenuList);
    }

    //将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus){
        LinkedList<SysMenuVo> sysMenuVoList = new LinkedList<>();
        menus.stream().forEach(sysMenu -> {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)){
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        });
        return sysMenuVoList;
    }
}
