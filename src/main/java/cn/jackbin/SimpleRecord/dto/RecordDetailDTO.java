package cn.jackbin.SimpleRecord.dto;

import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.vo
 * @date: 2020/10/14 23:02
 **/
@Data
@NoArgsConstructor
public class RecordDetailDTO extends RecordDetailDO {

    private static final long serialVersionUID = -1400861529425999732L;

    private String recordAccountName;

    private String sourceAccountName;

    private String targetAccountName;

}
