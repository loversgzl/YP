package com.party.thirdpartyproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author ：light
 * @date ：2024/11/26 15:49:08
 * @description : 云账户提现方式
 */
@Getter
@AllArgsConstructor
public enum YZHWithdrawChannelEnum {
    BANK_PAY("bankpay", "银行卡"),

    ALIPAY("alipay", "支付宝"),
    WXPAY("wxpay", "微信"),
    ;


    /**
     * 第三方平台编号
     */
    private final String channelEnName;

    /**
     * 第三方平台名称
     */
    private final String channelZhName;
}
