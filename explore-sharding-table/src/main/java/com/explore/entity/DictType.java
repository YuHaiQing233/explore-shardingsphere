package com.explore.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典类型实体类
 * @author HaiQing.Yu
 * @since 2025/5/14 10:28
 */
@Data
@TableName("dict_type")
public class DictType {

    private Long id;
    private String typeCode;
    private String typeName;
    private Integer status;
    private String remark;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}
