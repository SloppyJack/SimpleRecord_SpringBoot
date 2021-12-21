package cn.jackbin.SimpleRecord.dto;

import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RecordAccountAnalysisDTO extends RecordAccountDO {

    private static final long serialVersionUID = 4810168723585415780L;
    // 流入
    private Double inflow = 0.0;

    // 流出
    private Double outflow = 0.0;

}
