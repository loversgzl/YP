package com.party.thirdpartyproject.schedule;

import com.party.thirdpartyproject.enums.ThirdPartyTableNameEnum;
import com.party.thirdpartyproject.mapper.ThirdPartyInfoMapper;
import com.party.thirdpartyproject.mapper.ThirdPartyParamMapper;
import com.party.thirdpartyproject.service.DistUserThirdPartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ：light
 * @date ：2024/11/27 10:38:00
 * @description :
 */
@Slf4j
@Component
public class ScheduleDemo {

    @Resource
    private ThirdPartyInfoMapper thirdPartyInfoMapper;

    @Resource
    private ThirdPartyParamMapper thirdPartyParamMapper;

    @Resource
    private DistUserThirdPartyService distUserThirdPartyService;

//    @Scheduled(cron = "*/5 * * * * ?")
    public void task() {
        System.out.println("开始执行定时任务");
        distUserThirdPartyService.sqlTest();

        // 检查是否存在前置表，没有则直接创建
        List<Map<String, String>> tableList = thirdPartyInfoMapper.isTableExist(ThirdPartyTableNameEnum.THIRD_PARTY_INFO.getTableName());
        if (tableList.size() == 0) {
            log.info("数据库没有前置-第三方平台信息表，需要新建");
            thirdPartyInfoMapper.cleanTable();
            thirdPartyInfoMapper.initTable();
            thirdPartyInfoMapper.initTableRecord();
            log.info("第三方平台信息表-新建完成");
        }

        tableList = thirdPartyParamMapper.isTableExist(ThirdPartyTableNameEnum.THIRD_PARTY_PARAM.getTableName());
        if (tableList.size() == 0) {
            log.info("数据库没有前置-第三方平台参数表，需要新建");
            thirdPartyParamMapper.cleanTable();
            thirdPartyParamMapper.initTable();
            thirdPartyParamMapper.initTableRecord();
            log.info("第三方平台参数表-新建完成");
        }
        System.out.println("执行结束");
    }
}
