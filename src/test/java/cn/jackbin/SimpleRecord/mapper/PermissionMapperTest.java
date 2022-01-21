package cn.jackbin.SimpleRecord.mapper;

import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.entity.MenuDO;
import cn.jackbin.SimpleRecord.entity.RecordDetailDO;
import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.service.*;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.mapper
 * @date: 2020/8/19 21:36
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class PermissionMapperTest {
    @Autowired
    private MenuMapper mapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RecordUserCategoryService recordUserCategoryService;
    @Autowired
    private RecordBookService recordBookService;
    @Autowired
    private RecordAccountService recordAccountService;
    @Autowired
    private RecordDetailService recordDetailService;

    @Test
    public void queryPermissionByUserId() {
        List<MenuDO> list = mapper.queryUserMenus(2L);
        Assert.assertEquals(5,list.size());
    }

    @Test
    public void test(){
//        List<UserDO> userDOS = userService.getUsers();
//        for (int i=0; i<userDOS.size(); i++){
//            UserDO userDO = userDOS.get(i);
//            recordUserCategoryService.reset(userDO.getId().intValue());
//            // 4. 初始化账单
//            recordBookService.init(userDO.getId().intValue());
//            // 5. 初始化记账账户
//            recordAccountService.init(userDO.getId().intValue());
//            log.info("处理第{}个成功，id为{}",i+1, userDO.getId() );
//        }
//        String sql = "SELECT t1.*, t2.`name`, t3.type record_type, t3.name record_category, t4.id record_account_id, t5.id record_book_id\n" +
//                "FROM tmp_tb_record_detail t1 \n" +
//                "\tLEFT JOIN tmp_tb_spend_category t2 on t1.spend_category_id = t2.id\n" +
//                "\tLEFT JOIN tb_record_user_category t3 on t2.name = t3.name and t3.user_id = t1.user_id\n" +
//                "\tLEFT JOIN tb_record_account t4 on t4.user_id = t1.user_id and t4.order_no = '1'\n" +
//                "\tLEFT JOIN tb_record_book t5 on t5.user_id = t1.user_id and t5.order_no = '1'";
//        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//        for (int i=0; i<list.size(); i++) {
//            Map<String, Object> map = list.get(i);
//            RecordDetailDO recordDetailDO = new RecordDetailDO();
//            recordDetailDO.setUserId(((Long) map.get("user_id")).intValue());
//            recordDetailDO.setRecordAccountId(((Long) map.get("record_account_id")).intValue());
//            recordDetailDO.setRecordBookId(((Long) map.get("record_book_id")).intValue());
//            recordDetailDO.setRecordType(((Long) map.get("record_type")).intValue());
//            recordDetailDO.setRecordCategory((String) map.get("record_category"));
//            recordDetailDO.setAmount(((BigDecimal) map.get("amount")).doubleValue());
//            recordDetailDO.setOccurTime(new Date(((Timestamp)map.get("occur_time")).getTime()));
//            recordDetailDO.setRemark((String) map.get("remarks"));
//            recordDetailDO.setStatus(CommonConstants.STATUS_NORMAL);
//            if (map.get("create_time") != null){
//                recordDetailDO.setCreateTime(new Date(((Timestamp)map.get("create_time")).getTime()));
//            }
//            if (map.get("update_time") != null){
//                recordDetailDO.setUpdateTime(new Date(((Timestamp)map.get("update_time")).getTime()));
//            }
//
//            if (map.get("delete_time") != null){
//                recordDetailDO.setDeleteTime(new Date(((Timestamp)map.get("delete_time")).getTime()));
//            }
//            log.info("处理第{}个成功",i+1 );
//            recordDetailService.save(recordDetailDO);
//        }
//        log.info("处理成功");
    }
}
