package cn.jackbin.SimpleRecord.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 分页返回封装类
 * @date: 2020/12/7 20:45
 **/
@Data
@AllArgsConstructor
public class PageBO<T> {
    List<T> list;

    int total;
}
