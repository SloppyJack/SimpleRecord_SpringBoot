package cn.jackbin.SimpleRecord.dto;

import cn.jackbin.SimpleRecord.entity.RecordBookDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecordBookAnalysisDTO extends RecordBookDO {

    // 收入
    private Double incomeTotal = 0.0;

    // 收入
    private Double expendTotal = 0.0;
}
