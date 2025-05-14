# explore-shardingsphere
探索Shardingsphere的使用

# 一、库表结构

1、新建库
-------
> 创建两个数据库 db0 与 db1


2、新建表
-------
> 1. 新建订单表（分别在两个数据库中新建4个表，orders_0、orders_1、orders_2、orders_3）
> 2. 新建用户表，在 db0 库中新建一个表，用作分库分表中的单表
> 3. 新建 字典类型表与字典数据表，用作分库分表中的广播表


3、分库分表配置
-------
```yaml
spring:
  shardingsphere:
    props:
      sql-show: true # 显示路由日志
    datasource:
      names: ds0, ds1
      ds0:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/xh-db-0?useSSL=false&serverTimezone=Asia/Shanghai
        username: root
        password: root
        # Druid 连接池优化参数
        initialSize: 5
        minIdle: 5
        maxActive: 20
      ds1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/xh-db-1?useSSL=false&serverTimezone=Asia/Shanghai
        username: root
        password: root
        # Druid 连接池优化参数
        initialSize: 5
        minIdle: 5
        maxActive: 20
    rules:
      sharding:
        key-generators:
          snowflake:
            type: SNOWFLAKE
            props:
              worker-id: 110
        tables:
          orders: # 分片表名
            key-generate-strategy:
              column: order_id
              key-generator-name: snowflake
            actual-data-nodes: ds${0..1}.orders_${0..3} # 物理节点分布
            database-strategy:
              standard:
                sharding-column: user_id   # 分库键
                sharding-algorithm-name: db_hash  # 分库算法
            table-strategy:
              standard:
                sharding-column: order_id
                sharding-algorithm-name: table_hash
        sharding-algorithms:
          db_hash:
            type: HASH_MOD  # 哈西取模算法
            props:
              sharding-count: 2   # 分2个库
          table_hash:
            type: HASH_MOD
            props:
              sharding-count: 4   # 每个库分4张表
```


4、单表配置
-------
```yaml
spring:
  shardingsphere:
    rules:
      sharding:
        # 非分片表默认路由到 ds0
        default-data-source-name: ds0
```


5、广播表配置
-----------
```yaml
spring:
  shardingsphere:
    rules:
      sharding:
        # 定义广播表
        broadcast-tables:
          - dict_type
          - dict_data
```

6、绑定表配置
-----------
```yaml
spring:
  shardingsphere:
    rules:
      sharding:
        tables:
          order_item:
            key-generate-strategy:
              column: item_id
              key-generator-name: snowflake
            actual-data-nodes: ds${0..1}.order_item_${0..3}
            database-strategy:
              standard:
                sharding-column: user_id   # 分库键
                sharding-algorithm-name: db_hash  # 分库算法
            table-strategy:
              standard:
                sharding-column: order_id
                sharding-algorithm-name: table_hash
        binding-tables:
          - orders, order_item  # 主表orders与子表order_item绑定
```


```mysql

--- 订单表
CREATE TABLE `orders` (
    `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `order_no` varchar(32) NOT NULL COMMENT '订单编号',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `order_status` tinyint NOT NULL COMMENT '订单状态(0:待支付,1:已支付待发货,2:已发货,3:已完成,4:已取消,5:已退款)',
    `total_amount` decimal(12,2) NOT NULL COMMENT '订单总金额',
    `payment_amount` decimal(12,2) NOT NULL COMMENT '实付金额',
    `freight_amount` decimal(10,2) DEFAULT '0.00' COMMENT '运费',
    `discount_amount` decimal(10,2) DEFAULT '0.00' COMMENT '优惠金额',
    `payment_type` tinyint DEFAULT NULL COMMENT '支付方式(1:支付宝,2:微信,3:银行卡)',
    `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
    `payment_serial_number` varchar(64) DEFAULT NULL COMMENT '支付流水号',
    `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
    `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
    `receiver_name` varchar(32) NOT NULL COMMENT '收货人姓名',
    `receiver_phone` varchar(20) NOT NULL COMMENT '收货人电话',
    `receiver_province` varchar(32) DEFAULT NULL COMMENT '省',
    `receiver_city` varchar(32) DEFAULT NULL COMMENT '市',
    `receiver_district` varchar(32) DEFAULT NULL COMMENT '区',
    `receiver_address` varchar(256) DEFAULT NULL COMMENT '详细地址',
    `note` varchar(512) DEFAULT NULL COMMENT '订单备注',
    `source` tinyint DEFAULT NULL COMMENT '订单来源(1:PC,2:APP,3:小程序,4:H5)',
    `delete_status` tinyint DEFAULT '0' COMMENT '删除状态(0:未删除,1:已删除)',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_order_no` (`order_no`),
    KEY `idx_create_time` (`create_time`)
) COMMENT='订单主表';

-- 订单字表
CREATE TABLE `order_item` (
    `item_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '订单商品ID',
    `order_id` bigint NOT NULL COMMENT '订单ID',
    `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
    `order_sn` varchar(32) NOT NULL COMMENT '订单编号',
    `product_id` bigint unsigned NOT NULL COMMENT '商品ID',
    `product_name` varchar(100) NOT NULL COMMENT '商品名称',
    `product_image` varchar(255) DEFAULT NULL COMMENT '商品图片',
    `product_spec` varchar(100) DEFAULT NULL COMMENT '商品规格',
    `product_price` decimal(10,2) NOT NULL COMMENT '商品单价',
    `quantity` int NOT NULL COMMENT '购买数量',
    `total_price` decimal(10,2) NOT NULL COMMENT '商品总价',
    `refund_status` tinyint DEFAULT '0' COMMENT '退款状态(0:无退款,1:退款中,2:已退款)',
    `refund_amount` decimal(10,2) DEFAULT '0.00' COMMENT '退款金额',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`item_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_product_id` (`product_id`)
) COMMENT='订单商品明细表';

-- 用户表
CREATE TABLE `users` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
     `username` varchar(50) NOT NULL COMMENT '用户名',
     `password` varchar(255) NOT NULL COMMENT '密码(加密存储)',
     `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
     `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
     `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
     `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(0:禁用,1:正常)',
     `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
     `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
     `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`),
     UNIQUE KEY `idx_username` (`username`),
     UNIQUE KEY `idx_email` (`email`),
     KEY `idx_phone` (`phone`)
) COMMENT='用户表';

-- 字典类型表
CREATE TABLE `dict_type` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '字典类型ID',
     `type_code` varchar(50) NOT NULL COMMENT '字典类型编码',
     `type_name` varchar(100) NOT NULL COMMENT '字典类型名称',
     `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(0:禁用,1:正常)',
     `remark` varchar(500) DEFAULT NULL COMMENT '备注',
     `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`),
     UNIQUE KEY `idx_type_code` (`type_code`)
) COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE `dict_data` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '字典项ID',
     `type_code` varchar(50) NOT NULL COMMENT '字典类型编码',
     `dict_code` varchar(50) NOT NULL COMMENT '字典项编码',
     `dict_value` varchar(100) NOT NULL COMMENT '字典项值',
     `dict_label` varchar(100) NOT NULL COMMENT '字典项标签',
     `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
     `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(0:禁用,1:正常)',
     `remark` varchar(500) DEFAULT NULL COMMENT '备注',
     `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`),
     UNIQUE KEY `idx_type_dict_code` (`type_code`,`dict_code`),
     KEY `idx_type_code` (`type_code`),
     KEY `idx_dict_code` (`dict_code`)
) COMMENT='字典数据表';

```