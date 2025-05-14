package com.explore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单 实体类
 * @author HaiQing.Yu
 * @since 2025/5/13 17:06
 */
@Data
@TableName("orders")
public class Order {

    @TableId(type = IdType.ASSIGN_ID)
    private Long orderId;
    private String orderNo;
    private Long userId;
    private Integer orderStatus;
    private BigDecimal totalAmount;
    private BigDecimal paymentAmount;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private Integer paymentType;
    private LocalDateTime paymentTime;
    private String paymentSerialNumber;
    private LocalDateTime deliveryTime;
    private LocalDateTime receiveTime;
    private String receiverName;
    private String receiverPhone;
    private String receiverProvince;
    private String receiverCity;
    private String receiverDistrict;
    private String receiverAddress;
    private String note;
    private Integer source;
    private Integer deleteStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
