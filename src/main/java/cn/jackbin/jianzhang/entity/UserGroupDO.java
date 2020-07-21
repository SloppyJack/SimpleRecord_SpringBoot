package cn.jackbin.jianzhang.entity;

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
@TableName("tb_user_group")
public class UserGroupDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分组id
     */
    private Integer groupId;


}
