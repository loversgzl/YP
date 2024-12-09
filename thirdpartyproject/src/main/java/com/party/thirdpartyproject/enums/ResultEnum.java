package com.party.thirdpartyproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：light
 * @date ：2024/11/26 09:01:00
 * @description : 统一结果返回码
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS_CODE(200, "正确"),
    DEFAULT_ERROR_CODE(-1, "业务错误"),
    PARAM_ERROR_CODE(400, "参数错误"),
    NO_AUTH_CODE(402, "禁止访问"),
    NOT_FOUND(404, "资源没找到"),
    SERVER_ERROR_CODE(500, "服务器错误"),
    NETWORK_DESERTED_PLEASE_TRY_AGAIN_LATER(600, "网络开小差了，请稍后再试。"),
    ;

    private int code;
    private String msg;

}
