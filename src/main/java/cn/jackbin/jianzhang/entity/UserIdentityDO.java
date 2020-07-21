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
@TableName("tb_user_identity")
public class UserIdentityDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id
     */
    private Integer userId;

    private String identityType;

    private String credential;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;


}
