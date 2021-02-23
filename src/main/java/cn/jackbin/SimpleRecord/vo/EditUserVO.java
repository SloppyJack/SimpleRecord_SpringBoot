package cn.jackbin.SimpleRecord.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2021/2/19 20:24
 **/
@ApiModel(value="EditUserVO对象", description="编辑用户对象")
@Data
@NoArgsConstructor
public class EditUserVO {
    private Integer id;

    private String nickname;

    private Integer sex;

    private String email;

    private List<Integer> roles;
}
