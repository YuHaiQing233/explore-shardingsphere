package com.explore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.explore.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单 Mapper接口类
 * @author HaiQing.Yu
 * @since 2025/5/13 17:07
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
