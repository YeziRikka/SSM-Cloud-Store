package com.itWk.admin.service;

import com.itWk.admin.param.AdminUserRequest;
import com.itWk.admin.pojo.AdminUser;

public interface AdminUserService {
    /**
     * 登录
     * @param adminUserRequest
     * @return
     */
    AdminUser login(AdminUserRequest adminUserRequest);
}
