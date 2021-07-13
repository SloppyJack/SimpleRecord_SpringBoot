package cn.jackbin.SimpleRecord.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@EqualsAndHashCode(callSuper = false)
@TableName("tb_common_log")
public class CommonLogDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 959898899499281387L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    /**
     * 业务类型编码
     */
    private String businessTypeCode;

    /**
     * 业务类型名
     */
    private String businessTypeName;

    /**
     * Java中调用的方法
     */
    private String method;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求Url
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 操作人IP
     */
    private String operIp;

    /**
     * 操作人地址
     */
    private String operAddress;

    /**
     * 操作人Id
     */
    private Integer operId;

    /**
     * 返回结果
     */
    private String jsonResult;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorMsg;

}
