package cn.jackbin.SimpleRecord.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_permission")
public class PermissionDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 权限名称，例如：访问首页
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限所属模块，例如：人员管理
     */
    private String moduleName;

    /**
     * 模块编码
     */
    private String moduleCode;

}
