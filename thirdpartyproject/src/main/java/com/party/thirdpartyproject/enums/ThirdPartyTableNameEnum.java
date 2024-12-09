package com.party.thirdpartyproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：light
 * @date ：2024/11/27 11:21:15
 * @description : 第三方表名枚举类
 */
@Getter
@AllArgsConstructor
public enum ThirdPartyTableNameEnum {
    THIRD_PARTY_INFO("third_party_info", "第三方平台信息表"),

    THIRD_PARTY_PARAM("third_party_param", "第三方平台参数表"),
    ;


    /**
    * 第三方平台编号
    */
    private final String tableName;

    /**
    * 第三方平台名称
    */
    private final String tableInfo;
}
