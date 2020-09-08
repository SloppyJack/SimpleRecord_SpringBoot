package cn.jackbin.SimpleRecord.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.dto
 * @date: 2020/9/8 20:41
 **/
@Data
public class PageDTO {
    /**
     * 分页的索引
     */
    @NotNull(message = "当前页数不能为空")
    int pageIndex;

    /**
     * 分页的大小
     */
    @NotNull(message = "分页的大小不能为空")
    int pageSize;
}
