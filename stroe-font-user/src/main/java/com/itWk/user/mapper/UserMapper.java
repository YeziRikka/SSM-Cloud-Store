package com.itWk.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itWk.POJO.User;

/**
 * 继承BaseMapper 传入user表，不用写具体查询方法便可以进行单表的操作
 */

public interface UserMapper extends BaseMapper<User> {
}
