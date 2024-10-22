package com.coderdream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.coderdream.entity.UserEntity;
import com.coderdream.mapper.UserMapper;
import com.coderdream.service.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserService userService;

    @Test
    void testQueryWrapper() {
//        // 1.构建查询条件 where name like "%o%" AND balance >= 1000
//        QueryWrapper<AppEntity> wrapper = new QueryWrapper<AppEntity>()
//            .select("id", "title")
//            .like("title", "搜");
////            .ge("balance", 1000);
//        // 2.查询数据
//        List<AppEntity> users = userMapper.selectList(wrapper);
//        users.forEach(System.out::println);

        UserEntity user = userMapper.selectById(1);
        System.out.println(user.toString());
    }

    @Test
    void testSelectById() {
//        // 1.构建查询条件 where name like "%o%" AND balance >= 1000
//        QueryWrapper<AppEntity> wrapper = new QueryWrapper<AppEntity>()
//            .select("id", "title")
//            .like("title", "搜");
////            .ge("balance", 1000);
//        // 2.查询数据
//        List<AppEntity> users = appMapper.selectList(wrapper);
//        users.forEach(System.out::println);

        UserEntity user = userMapper.selectById(1);
        System.out.println(user.toString());
    }

    public static Integer SIZE = 100000;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testAddOrUpdateUsers() {
        long startTime = System.currentTimeMillis();
        // 准备用户数据
        List<UserEntity> users = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) {
            UserEntity user = new UserEntity();
            user.setUsername("user" + i);
            user.setPassword("" + (int) (Math.random() * SIZE)); // 随机分数
            users.add(user);
        }

        // 批量新增或更新用户
        boolean isSavedOrUpdated = userService.saveOrUpdateBatch(users);
        assertTrue(isSavedOrUpdated); // 验证是否插入或更新成功

        // 验证数据库是否存储了 1000 个用户
        List<UserEntity> allUsers = userService.list();
        assertEquals(SIZE, allUsers.size());

        // 再次进行批量更新（模拟更新操作）
        for (UserEntity user : users) {
            user.setPassword("" + (int) (Math.random() * 100) + SIZE);  // 更新分数为 100 到 200 之间
        }

        boolean isUpdated = userService.saveOrUpdateBatch(users);
        assertTrue(isUpdated); // 验证是否更新成功
        long endTime = System.currentTimeMillis();
        long period = endTime - startTime;
        log.info("执行时间：{} 毫秒", period);
    }
}
