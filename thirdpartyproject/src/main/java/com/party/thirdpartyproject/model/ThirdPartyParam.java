package com.party.thirdpartyproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author ：light
 * @date ：2024/11/26 11:07:59
 * @description : 第三方平台参数表
 */
@Data
public class ThirdPartyParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 第三方平台编号
     */
    private String thirdPartyNo;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 参数信息
     */
    private String paramInfo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate gmtModified;

    public static final String THIRD_PARTY_NO = "third_party_no";

}
