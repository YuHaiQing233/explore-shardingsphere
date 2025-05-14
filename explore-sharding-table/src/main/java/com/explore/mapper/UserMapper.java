package com.explore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.explore.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper接口类
 *
 * @author HaiQing.Yu
 * @since 2025/5/14 10:11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
