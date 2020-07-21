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
@TableName("tb_group")
public class GroupDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 分组名称，例如：搬砖者
     */
    private String name;

    /**
     * 分组信息：例如：搬砖的人
     */
    private String info;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;


}
