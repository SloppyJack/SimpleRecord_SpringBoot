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
@TableName("tb_common_log")
public class CommonLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String message;

    private Integer userId;

    private Integer statusCode;

    private String method;

    private String path;

    private String permission;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;


}
