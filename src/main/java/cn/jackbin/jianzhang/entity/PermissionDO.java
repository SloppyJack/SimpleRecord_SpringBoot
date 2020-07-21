package cn.jackbin.jianzhang.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

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
public class PermissionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 权限名称，例如：访问首页
     */
    private String name;

    /**
     * 权限所属模块，例如：人员管理
     */
    private String module;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;


}
