package com.fuswx.spzx.manager.service.impl;

import com.fuswx.spzx.manager.mapper.SysRoleMapper;
import com.fuswx.spzx.manager.mapper.SysRoleUserMapper;
import com.fuswx.spzx.manager.service.SysRoleService;
import com.fuswx.spzx.model.dto.system.SysRoleDto;
import com.fuswx.spzx.model.entity.system.SysRole;
import com.fuswx.spzx.model.vo.common.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    //1、角色列表的方法
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有数据
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);
        System.out.println(list);
        //封装pageInfo对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //2、角色添加的方法
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    //3、角色修改的方法
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    //4、角色删除的方法
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.delete(roleId);
    }

    //查询所有角色
    @Override
    public Map<String, Object> findAll(Integer userId) {
        //1、查询所有角色
        List<SysRole> roleList = sysRoleMapper.findAll();

        //2、分配过的角色列表
        //根据userId查询用户分配过的角色id列表
        List<Integer> roleIds = sysRoleUserMapper.selectRoleIdsByUserId(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("allRoleList", roleList);
        map.put("sysUserRoles", roleIds);

        return map;
    }
}
