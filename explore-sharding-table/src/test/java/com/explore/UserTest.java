package com.explore;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.explore.entity.User;
import com.explore.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户业务 测试类
 * @author HaiQing.Yu
 * @since 2025/5/14 10:12
 */
@Slf4j
@SpringBootTest
public class UserTest {

    @Resource
    private UserMapper userMapper;


    @Test
    public void createUser() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        user.setEmail("zhangsan@163.com");
        user.setPhone("13000000000");
        user.setAvatar("http://ep.com/avatar.jpg");
        user.setStatus(1);
        user.setCreatedTime(LocalDateTime.now());
        userMapper.insert(user);
    }


    @Test
    public void queryUser() {
        List<User> users = userMapper.selectList(null);
        log.info("users:{}", users);
    }

}
