package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.dto.RecordAccountAnalysisDTO;
import cn.jackbin.SimpleRecord.entity.RecordAccountDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service
 * @date: 2020/11/23 21:35
 **/
public interface RecordAccountService extends IService<RecordAccountDO> {

    List<RecordAccountDO> getListByUserId(Integer userId);

    void add(Integer userId, Integer type, String name, Integer inNetAssets, Integer orderNo);

    void update(Long id, Integer type, String name, Integer inNetAssets, Integer orderNo);

    /**
     * 获取各账户的流入流出金额
     */
    List<RecordAccountAnalysisDTO> analysisAccounts(Integer userId);
}
