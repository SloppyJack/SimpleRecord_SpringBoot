package cn.jackbin.SimpleRecord.controller.basic;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Api(value = "CommonLogController", tags = { "日志相关接口" })
@RestController
@RequestMapping("/log")
public class CommonLogController {

}
