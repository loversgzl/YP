package com.party.thirdpartyproject.mapper;

import com.party.thirdpartyproject.model.ThirdPartyParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author ：light
 * @date ：2024/11/27 09:07:07
 * @description :
 */
@Mapper
public interface ThirdPartyParamMapper {
    @Update("DROP TABLE IF EXISTS `third_party_param`;")
    void cleanTable();

    @Update("CREATE TABLE `third_party_param` ( `id` bigint NOT NULL PRIMARY KEY AUTO_INCREMENT, `third_party_no` varchar(100) NOT NULL COMMENT '三方平台编号', `param_name` varchar(100) NOT NULL COMMENT '参数名称', `param_value` varchar(3000) NOT NULL COMMENT '参数值', `param_info` varchar(100) COMMENT '参数信息', `remark` varchar(1000) COMMENT '备注', `gmt_create` datetime DEFAULT now() COMMENT '创建时间', `gmt_modified` datetime DEFAULT now() COMMENT '修改时间' ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='第三方平台参数表';")
    void initTable();

    @Select("SHOW TABLES LIKE #{tableName}")
    List<Map<String, String>> isTableExist(@Param("tableName") String tableName);

    List<ThirdPartyParam> listParamByThirdNo(@Param("thirdPartyNo") String thirdPartyNo);

    void initTableRecord();
}
