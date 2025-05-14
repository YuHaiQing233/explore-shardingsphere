package com.explore.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典数据实体类
 * @author HaiQing.Yu
 * @since 2025/5/14 11:11
 */
@Data
@TableName("dict_data")
public class DictData {

    private Long id;
    private String typeCode;
    private String dictCode;
    private String dictValue;
    private String dictLabel;
    private Integer sort;
    private Integer status;
    private String remark;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}
