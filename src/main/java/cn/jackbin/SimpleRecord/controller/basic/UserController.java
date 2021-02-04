package cn.jackbin.SimpleRecord.controller.basic;


import cn.jackbin.SimpleRecord.bo.PageBO;
import cn.jackbin.SimpleRecord.constant.CodeMsg;
import cn.jackbin.SimpleRecord.constant.SexConstant;
import cn.jackbin.SimpleRecord.dto.UserRoleDTO;
import cn.jackbin.SimpleRecord.entity.RoleDO;
import cn.jackbin.SimpleRecord.service.RoleService;
import cn.jackbin.SimpleRecord.vo.*;
import cn.jackbin.SimpleRecord.vo.PageVO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.UserService;
import cn.jackbin.SimpleRecord.utils.PasswordUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Api(value = "UserController", tags = { "用户管理" })
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "分页获取用户列表")
    @PostMapping(value = "/page")
    public Result<?> getByPage(@RequestBody @Validated GetUsersVO vo) {
        PageBO<UserDO> pageBO = userService.getByPage(vo.getUsername(), vo.getDeleted(), vo.getDate(),
                vo.getPageNo() - 1, vo.getPageSize());
        return Result.success(pageBO);
    }

    @ApiOperation(value = "根据用户编号获取详细信息")
    @GetMapping(value = "/{userId}")
    public Result<?> getUserById(@Validated @Positive @PathVariable(value = "userId") Integer userId) {
        UserDO userDO = userService.getById(userId);
        UserRoleDTO ur = new UserRoleDTO();
        BeanUtils.copyProperties(userDO, ur);
        List<RoleDO> list = roleService.getByUserId(Long.valueOf(userId));
        ur.setRoles(list);
        return Result.success(ur);
    }
}
