package com.itWk.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itWk.Utils.MD5Util;
import com.itWk.admin.mapper.AdminUserMapper;
import com.itWk.admin.param.AdminUserRequest;
import com.itWk.admin.pojo.AdminUser;
import com.itWk.admin.service.AdminUserService;
import com.itWk.constants.UserContants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实现类
 */
@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;
    /**
     * 登录
     *
     * @param adminUserRequest
     * @return
     */
    @Override
    public AdminUser login(AdminUserRequest adminUserRequest) {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", adminUserRequest.getUserAccount());
        //密码加密处理
        queryWrapper.eq("user_password", MD5Util.encode(adminUserRequest.getUserPassword()
                                                     + UserContants.UESR_SLAT));
        AdminUser user = adminUserMapper.selectOne(queryWrapper);
        log.info("AdminUserServiceImpl.login业务结束，结果:{}",user);
        return user;
    }
}
