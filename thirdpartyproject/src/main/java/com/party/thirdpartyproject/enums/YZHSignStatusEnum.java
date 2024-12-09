package com.party.thirdpartyproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：light
 * @date ：2024/11/25 16:21:42
 * @description : 云账户签约状态
 */
@Getter
@AllArgsConstructor
public enum YZHSignStatusEnum {
    UN_SIGN(0, "未签约"),

    SIGNED(1, "已签约"),
    TERMINATE_CONTRACT(2, "已解约"),
    ;


    /**
     * 第三方平台编号
     */
    private final int signStatus;

    /**
     * 第三方平台名称
     */
    private final String signInfo;
}
