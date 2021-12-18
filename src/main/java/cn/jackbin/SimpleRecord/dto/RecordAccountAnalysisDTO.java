package cn.jackbin.SimpleRecord.dto;

import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RecordAccountAnalysisDTO extends RecordAccountDO {

    // 流入
    private Double inflow = 0.0;

    // 流出
    private Double outflow = 0.0;

}
