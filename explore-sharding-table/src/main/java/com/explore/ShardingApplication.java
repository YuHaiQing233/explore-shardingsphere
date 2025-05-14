package com.explore;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author HaiQing.Yu
 * @since 2025/5/13 15:29
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class ShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication.class, args);
    }

}
