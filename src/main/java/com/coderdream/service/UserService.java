package com.coderdream.service;

//import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coderdream.entity.UserEntity;

public interface UserService extends IService<UserEntity> {

    UserEntity selectById(int id);
    Page<UserEntity> getUserListByUsername(String username, int current, int size);

    IPage<UserEntity> getUsersWithPagination(int currentPage, int pageSize);
}
