package com.party.thirdpartyproject.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ：light
 * @date ：2024/11/26 11:30:20
 * @description : 初始化表结构，初始化表数据
 */
@Component
public class CreateTableUtil {

    @Value("${spring.datasource.url}")
    public String DATA_SOURCE_URL;

    @Value("${spring.datasource.username}")
    public String DATA_SOURCE_USER_NAME;

    @Value("${spring.datasource.password}")
    public String DATA_SOURCE_PASSWORD;

    @Value("${spring.datasource.driver‐class‐name}")
    public String DATA_SOURCE_DRIVER;

    /**
     * 如果是新引入的此jar包，则创建 云账户 需要的表格
     * */
    public void creatYunZhangHuTables(){

    }


}
