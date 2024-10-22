package com.coderdream;

import com.coderdream.entity.UserEntity;
import com.coderdream.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class MyBatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectUser(){
        UserEntity user = userMapper.selectById(1);
        System.out.println(user.toString());
    }
}
