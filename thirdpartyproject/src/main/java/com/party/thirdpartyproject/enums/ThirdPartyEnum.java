package com.party.thirdpartyproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：light
 * @date ：2024/11/22 15:04:44
 * @description : 第三方签约打款平台枚举类
 */
@Getter
@AllArgsConstructor
public enum ThirdPartyEnum {
    YUN_ZHANG_HU("1001", "云账户平台"),

    RU_FENG_WEI("1002", "儒丰伟平台"),
    ;


    /**
    * 第三方平台编号
    */
    private final String thirdPartyNo;

    /**
    * 第三方平台名称
    */
    private final String thirdPartyName;
}
