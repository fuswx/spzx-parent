package com.fuswx.spzx.manager.utils;

import com.fuswx.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

//封装树型菜单数据
public class MenuHelper {

    //递归实现封装过程
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){
        //sysMenuList所有菜单集合
        //创建List集合，用于封装最终的数据
        ArrayList<SysMenu> trees = new ArrayList<>();
        //遍历所有菜单集合
        sysMenuList.stream()
                .filter(sysMenu -> {  //找到递归操作的的入口，第一层的菜单，条件：parent_id = 0
            return sysMenu.getParentId().intValue() == 0;
        }).forEach(sysMenu -> {
            //根据第一层，找下层数据，使用递归完成
            //写方法实现找下层过程，
            // 方法里面传递两个参数，第一个参数为当前第一层的菜单，第二个参数是所有菜单集合
            trees.add(findChildren(sysMenu, sysMenuList));
                });

        return trees;
    }

    //递归查询下层菜单
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        //SysMenu有属性private List<SysMenu> children;封装下一层数据
        sysMenu.setChildren(new ArrayList<>());
        //递归查找
        //sysMenu的id值和sysMenuList中parentId相同
        sysMenuList.stream()
                .filter(currentSysMenu -> currentSysMenu.getParentId().intValue() == sysMenu.getId().intValue())
                //currentSysMenu就是下层数据，进行封装
                .forEach(currentSysMenu -> sysMenu.getChildren().add(findChildren(currentSysMenu, sysMenuList)));
        return sysMenu;
    }
}
