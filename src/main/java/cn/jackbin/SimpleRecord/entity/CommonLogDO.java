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
@TableName("tb_common_log")
public class CommonLogDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String message;

    private Integer userId;

    private Integer statusCode;

    private String method;

    private String path;

    private String permission;

}
