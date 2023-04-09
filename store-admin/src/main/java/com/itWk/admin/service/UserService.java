package com.itWk.admin.service;


import com.itWk.POJO.User;
import com.itWk.Utils.Result;
import com.itWk.param.CartListRequest;
import com.itWk.param.PageRequest;

public interface UserService {
    /**
     * 用户展示业务方法
     * @param pageRequest
     * @return
     */
    Result userList(PageRequest pageRequest);

    /**
     * 根据id删除用户数据
     * @param cartListRequest
     * @return
     */
    Result userRemove(CartListRequest cartListRequest);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    Result userUpdate(User user);

    /**
     * 用户添加功能
     * @param user
     * @return
     */
    Result userSave(User user);
}
