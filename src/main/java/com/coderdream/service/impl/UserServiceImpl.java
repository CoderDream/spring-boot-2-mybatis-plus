package com.coderdream.service.impl;

//import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coderdream.entity.UserEntity;
import com.coderdream.mapper.UserMapper;
import com.coderdream.service.UserService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserEntity selectById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public Page<UserEntity> getUserListByUsername(String username, int current, int size) {
        Page<UserEntity> page = this.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(current, size), null);
        if (username != null) {
            return this.baseMapper.selectByUsername(page, username);
        }
        return page;
    }

    @Override
    public IPage<UserEntity> getUsersWithPagination(int currentPage, int pageSize) {
        Page<UserEntity> page = new Page<>(currentPage, pageSize);
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        return page(page, queryWrapper);
    }
}
