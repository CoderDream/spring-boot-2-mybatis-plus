package com.coderdream;

import com.coderdream.entity.UserEntity;
import com.coderdream.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(UserController.class)
//@Import(MyBatisPlusConfig2.class)
@SpringBootTest(classes = AppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // 用于模拟MVC层

    @MockBean
    private UserService userService; // 用于模拟服务层

    private UserEntity testUser; // 测试用的用户对象

    /**
     * 在每个测试之前初始化数据。
     */
    @BeforeEach
    public void setUp() {
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setUsername("John Doe");
        testUser.setPassword("password");
    }

    /**
     * 测试获取所有用户信息。
     * 应当返回一个包含所有用户的列表。
     *
     * @throws Exception 如果发生异常
     */
    @Test
    public void shouldReturnAllUsersWhenGetAllUsers() throws Exception {
        List<UserEntity> users = new ArrayList<>();
        users.add(testUser); // 假设只有一个用户

        when(userService.list()).thenReturn(users);

        ResultActions result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].username", is(testUser.getUsername())));

        verify(userService).list(); // 验证userService.list()被调用了一次
    }

    /**
     * 测试根据ID获取用户信息。
     * 应当返回指定ID的用户信息，如果不存在则返回404。
     *
     * @throws Exception 如果发生异常
     */
    @Test
    public void shouldReturnUserWhenGetUserById() throws Exception {
        when(userService.getById(testUser.getId())).thenReturn(testUser);

        mockMvc.perform(get("/api/users/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(testUser.getUsername())));
    }

    /**
     * 测试创建新用户。
     * 应当返回创建成功的响应，并包含位置头指向新创建的资源。
     *
     * @throws Exception 如果发生异常
     */
    @Test
    public void shouldCreateNewUserWhenPostNewUser() throws Exception {
        when(userService.save(testUser)).thenReturn(true);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/" + testUser.getId()));
    }

    /**
     * 测试更新用户信息。
     * 应当返回更新后的用户信息。
     *
     * @throws Exception 如果发生异常
     */
    @Test
    public void shouldUpdateUserWhenPutUser() throws Exception {
        when(userService.updateById(testUser)).thenReturn(true);

        mockMvc.perform(put("/api/users/{id}", testUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testUser)))
                .andExpect(status().isOk());
    }

    /**
     * 测试删除用户。
     * 应当返回无内容响应。
     *
     * @throws Exception 如果发生异常
     */
    @Test
    public void shouldDeleteUserWhenDeleteUserById() throws Exception {
        System.out.println(testUser.getId());
//        doNothing().when(userService).removeById(testUser.getId());

        mockMvc.perform(delete("/api/users/{id}", testUser.getId()))
                .andExpect(status().isNoContent());
    }

    /**
     * 测试分页查询用户。
     * 应当返回分页的结果。
     *
     * @throws Exception 如果发生异常
     */
    @Test
    public void shouldReturnPagedResultsWhenGetUsersWithPagination() throws Exception {
        List<UserEntity> users = new ArrayList<>();
        users.add(testUser);

        Page<UserEntity> pageResult = new Page<>(1, 10);
        pageResult.setRecords(users);

        when(userService.getUsersWithPagination(1, 10)).thenReturn(pageResult);

        mockMvc.perform(get("/api/users/page")
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.records[0].username", is(testUser.getUsername())));
    }

    /**
     * 辅助方法，用于序列化Java对象为JSON字符串。
     *
     * @param obj 要序列化的对象
     * @return JSON字符串
     */
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
