package com.party.thirdpartyproject.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：light
 * @date ：2024/11/22 17:01:18
 * @description : 云平台-证件类型列表
 */
@Getter
@AllArgsConstructor
public enum YZHCertificateTypeEnum {
    ID_CARD(0, "身份证"),
    CERTIFICATE_TYPE_1(1, "台湾身份证"),
    CERTIFICATE_TYPE_2(2, "港澳居民来往内地通行证"),
    CERTIFICATE_TYPE_3(3, "护照"),
    CERTIFICATE_TYPE_5(5, "台湾居民来往大陆通行证"),
    CERTIFICATE_TYPE_9(9, "港澳居民居住证"),
    CERTIFICATE_TYPE_10(10, "台湾居民居住证"),
    CERTIFICATE_TYPE_11(11, "外国人永久居留身份证"),
    CERTIFICATE_TYPE_12(12, "外国人工作许可证"),

    ;


    /**
     * 云账户：证件类型编码
     */
    private final int CertificateType;

    /**
     * 云账户：证件类型编码说明
     */
    private final String CertificateName;
}
