package com.explore;

import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.explore.entity.Order;
import com.explore.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 订单 测试类
 * @author HaiQing.Yu
 * @since 2025/5/13 17:54
 */
@Slf4j
@SpringBootTest
public class OrderTest {

    @Resource
    private OrderMapper orderMapper;


    @Test
    public void createOrder() {

        for (int i = 0; i < 10; i++) {

            Order order = new Order();
            order.setOrderId(ThreadLocalRandom.current().nextLong());
            order.setOrderNo("OM" + String.format("%05d", i));
            order.setUserId((long) i);
            order.setOrderStatus(ThreadLocalRandom.current().nextInt(0, 6));
            order.setTotalAmount(BigDecimal.valueOf(100));
            order.setPaymentAmount(BigDecimal.valueOf(80));
            order.setFreightAmount(BigDecimal.valueOf(5));
            order.setDiscountAmount(BigDecimal.valueOf(20));
            order.setPaymentType(ThreadLocalRandom.current().nextInt(0, 2));
            order.setPaymentTime(LocalDateTime.now().plusDays(ThreadLocalRandom.current().nextInt(-5, 6)));
            order.setPaymentSerialNumber(String.format("%05d", ThreadLocalRandom.current().nextInt(1, 9999)));
            order.setDeliveryTime(order.getPaymentTime().plusDays(ThreadLocalRandom.current().nextInt(1, 3)));
            order.setReceiveTime(order.getDeliveryTime().plusDays(ThreadLocalRandom.current().nextInt(1, 2)));
            order.setReceiverName("张" + i);
            order.setReceiverPhone("136" + ThreadLocalRandom.current().nextInt(10000000, 100000000));
            order.setReceiverCity("-");
            order.setReceiverDistrict("-");
            order.setReceiverAddress("-");
            order.setNote("-");
            order.setSource(ThreadLocalRandom.current().nextInt(1, 4));
            order.setDeleteStatus(0);
            order.setCreateTime(LocalDateTime.now());
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.insert(order);
        }

    }

    @Test
    public void queryOrder() {

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
//        wrapper.eq("order_id", 91194872858329332L);
//        wrapper.in("user_id", 0, 1, 2, 3);
        wrapper.between("create_time", LocalDateTime.parse("2025-05-13 18:35:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), LocalDateTime.parse("2025-05-13 18:35:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        List<Order> orders = orderMapper.selectList(wrapper);
        log.info("订单列表: {}", orders);

    }

}
