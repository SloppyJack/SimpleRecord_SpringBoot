package cn.jackbin.SimpleRecord.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 分页返回封装类
 * @date: 2020/12/7 20:45
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBO<T> {

    public PageBO(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    List<T> list;

    // 页数
    int pageNo;

    // 分页大小
    int pageSize;

    // 总数
    int total;

    public int beginPosition() {
        return (pageNo -1) * pageSize;
    }
}
