package com.explore.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单字表 实体类
 * @author HaiQing.Yu
 * @since 2025/5/14 14:11
 */
@Data
@TableName("order_item")
public class OrderItem {

    private Long itemId;
    private Long orderId;
    private Long userId;
    private String orderSn;
    private Long productId;
    private String productName;
    private String productImage;
    private String productSpec;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Integer refundStatus;
    private BigDecimal refundAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
