package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.bo.MenuBO;
import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.common.LocalUser;
import cn.jackbin.SimpleRecord.common.LocalUserId;
import cn.jackbin.SimpleRecord.common.anotations.LoginRequired;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.SexConstant;
import cn.jackbin.SimpleRecord.dto.UserRoleDTO;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.service.MenuService;
import cn.jackbin.SimpleRecord.service.RoleService;
import cn.jackbin.SimpleRecord.service.UserRoleService;
import cn.jackbin.SimpleRecord.utils.PasswordUtil;
import cn.jackbin.SimpleRecord.vo.*;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Api(value = "UserController", tags = { "用户管理接口" })
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private MenuService menuService;

    @LoginRequired
    @ApiOperation(value = "获取用户的角色及菜单")
    @GetMapping("/roleMenus")
    public Result<?> getRoleMenus() {
        // 用户角色
        List<RoleDO> roleDOS = roleService.getByUserId(LocalUserId.get());
        // 用户菜单权限
        List<MenuDO> menuDOS = menuService.getUserMenus(LocalUserId.get());
        List<MenuBO> menuBOS =  menuService.copyFromMenuDos(menuDOS);
        List<MenuBO> tree = menuService.generatorMenuTree(menuBOS);
        RoleMenuVO roleMenuVO = new RoleMenuVO(LocalUser.get(), roleDOS, tree);
        return Result.success(roleMenuVO);
    }

    @ApiOperation(value = "分页获取用户列表")
    @PreAuthorize("hasAuthority('system:user:view')" )
    @PostMapping(value = "/page")
    public Result<?> getByPage(@RequestBody @Validated GetUsersVO vo) {
        PageBO<UserDO> pageBO = new PageBO<>(vo.getPageNo(), vo.getPageSize());
        userService.getByPage(vo.getUsername(), vo.getDeleted(), vo.getDate(), pageBO);
        return Result.success(pageBO);
    }

    @ApiOperation(value = "根据用户编号获取详细信息")
    @PreAuthorize("hasAuthority('system:user:detail')" )
    @GetMapping(value = "/{userId}")
    public Result<?> getUserById(@Validated @PositiveOrZero @PathVariable(value = "userId") Integer userId) {
        UserRoleDTO ur = new UserRoleDTO();
        if (userId > 0) {
            UserDO userDO = userService.getById(userId);
            BeanUtils.copyProperties(userDO, ur);
            List<RoleDO> list = roleService.getByUserId(Long.valueOf(userId));
            ur.setOwnedRoles(list);
        }
        ur.setAllRoles(roleService.getRoles());
        return Result.success(ur);
    }

    @ApiOperation(value = "编辑用户")
    @PreAuthorize("hasAuthority('system:user:edit')" )
    @PutMapping(value = "/edit")
    public Result<?> editUser(@RequestBody @Validated EditUserVO vo) {
        if (SexConstant.MAN !=vo.getSex() && SexConstant.WOMAN != vo.getSex()) {
            return Result.error(CodeMsg.SEX_FORMAT_ERROR);
        }
        // 编辑用户
        userService.edit(vo.getId(), vo.getNickname(), vo.getSex(), vo.getEmail());
        // 编辑用户角色
        userRoleService.edit(vo.getId(), vo.getRoles());
        return Result.success();
    }

    @ApiOperation(value = "添加用户")
    @PreAuthorize("hasAuthority('system:user:add')" )
    @PostMapping(value = "/add")
    public Result<?> addUser(@RequestBody @Validated AddUserVO vo) {
        if (userService.getByName(vo.getUsername()) != null) {
            return Result.error(CodeMsg.USERNAME_EXIST);
        }
        if (!PasswordUtil.check(vo.getCredential())) {
            return Result.error(CodeMsg.PSW_FORMAT_ERROR);
        }
        if (SexConstant.MAN !=vo.getSex() && SexConstant.WOMAN != vo.getSex()) {
            return Result.error(CodeMsg.SEX_FORMAT_ERROR);
        }
        /// 新增用户
        userService.add(vo.getUsername(), vo.getNickname(), vo.getSex(), vo.getEmail(), vo.getCredential());
        // 编辑用户角色
        UserDO userDO = userService.getByName(vo.getUsername());
        userRoleService.edit(userDO.getId().intValue(), vo.getRoles());
        return Result.success();
    }

    @ApiOperation(value = "删除用户")
    @PreAuthorize("hasAuthority('system:user:del')" )
    @DeleteMapping("/{id}")
    public Result<?> delUser(@PathVariable @Validated @Positive(message = "用户Id需为正数") Integer id) {
        userService.removeById(id);
        return Result.success();
    }

    @ApiOperation(value = "恢复用户")
    @PutMapping("/reset/{id}")
    @PreAuthorize("hasAuthority('system:user:reset')" )
    public Result<?> resetUser(@PathVariable @Validated @Positive(message = "用户Id需为正数") Integer id) {
        userService.reset(id);
        return Result.success();
    }
}
