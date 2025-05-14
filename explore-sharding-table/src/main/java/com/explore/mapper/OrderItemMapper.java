package com.explore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.explore.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单字表 Mapper接口类
 * @author HaiQing.Yu
 * @since 2025/5/14 14:14
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
