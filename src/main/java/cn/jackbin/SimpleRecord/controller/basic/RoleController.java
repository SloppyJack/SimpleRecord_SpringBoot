package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.constant.MenuConstants;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.service.MenuService;
import cn.jackbin.SimpleRecord.service.RoleService;
import cn.jackbin.SimpleRecord.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "获取用户的角色及菜单")
    @GetMapping("/menus")
    public Result<?> getRoleMenus() {
        // 用户角色
        List<RoleDO> roleDOS = roleService.getByUserId(LocalUserId.get());
        // 用户菜单权限
        List<MenuDO> menuDOS = menuService.getUserMenus(LocalUserId.get());
        List<MenuVO> tree = generatorTree(menuDOS);
        RoleMenuVO roleMenuVO = new RoleMenuVO(roleDOS, tree);
        return Result.success(roleMenuVO);
    }

    @ApiOperation(value = "获取角色列表")
    @PostMapping("/list")
    public Result<?> getRoleList(@RequestBody @Validated GetRolesVO vo) {
        return Result.success(roleService.getList(vo.getName(), vo.getDeleted(), vo.getDate(), vo.getPageNo() - 1, vo.getPageSize()));
    }

    private List<MenuVO> generatorTree(List<MenuDO> list) {
        // 复制属性
        List<MenuVO> voList = new ArrayList<>();
        list.forEach(n -> {
            MenuVO temp = new MenuVO();
            BeanUtils.copyProperties(n, temp);
            temp.setId(n.getId().intValue());
            if (MenuConstants.OC.equals(n.getOuterChain())) {
                temp.setOuterChain(true);
            }
            voList.add(temp);
        });
        List<MenuVO> tree = list2Tree(voList, null);
        sortTree(tree);
        return tree;
    }

    /**
     * list转为树结构
     */
    private List<MenuVO> list2Tree(List<MenuVO> list, Integer pId) {
        List<MenuVO> tree = new ArrayList<>();
        Iterator<MenuVO> it = list.iterator();
        while (it.hasNext()) {
            MenuVO m = it.next();
            if (m.getParentId() == pId) {
                tree.add(m);
                // 已添加的元素删除掉
                it.remove();
            }
        }
        // 寻找子元素
        tree.forEach(n -> n.setChildren(list2Tree(list, n.getId())));
        return tree;
    }

    /**
     * 排序树结构中的同级元素
     */
    private void sortTree(List<MenuVO> list) {
        // 当前层级元素的个数大于1才需要比较
        if (list.size() > 1) {
            // 冒泡排序
            for (int i=0; i<list.size() -1; i++) {
                for (int j=0; j<list.size()- i -1; j++) {
                    if (list.get(j).getOrderNo() > list.get(j + 1).getOrderNo()) {
                        // 交换
                        Collections.swap(list, j, j + 1);
                    }
                }
            }
        }
        // 则继续向下
        list.forEach(n -> sortTree(n.getChildren()));
    }
}
