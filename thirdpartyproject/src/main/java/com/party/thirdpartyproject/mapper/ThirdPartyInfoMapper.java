package com.party.thirdpartyproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author ：light
 * @date ：2024/11/26 11:19:50
 * @description :
 */
@Mapper
public interface ThirdPartyInfoMapper {
    @Update("DROP TABLE IF EXISTS `third_party_info`;")
    void cleanTable();

    @Update("CREATE TABLE `third_party_info` ( `id` bigint NOT NULL PRIMARY KEY AUTO_INCREMENT, `third_party_no` varchar(100) NOT NULL COMMENT '第三方平台编号', `third_party_name` varchar(100) COMMENT '平台名称', `third_party_info` varchar(100) COMMENT '平台信息', `remark` varchar(1000) COMMENT '备注', `gmt_create` datetime DEFAULT now() COMMENT '创建时间', `gmt_modified` datetime DEFAULT now() COMMENT '修改时间' ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='第三方平台信息表';")
    void initTable();

    @Select("SHOW TABLES LIKE #{tableName}")
    List<Map<String, String>> isTableExist(@Param("tableName") String tableName);

    void initTableRecord();
}
