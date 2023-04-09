package com.itWk.admin.service.impl;

import com.itWk.Clients.UserClient;
import com.itWk.POJO.User;
import com.itWk.Utils.Result;
import com.itWk.admin.service.UserService;
import com.itWk.param.CartListRequest;
import com.itWk.param.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserClient userClient;
    /**
     * 用户展示业务方法
     * @param pageRequest
     * @return
     */
    @Cacheable(value = "list.user",key = "#pageRequest.currentPage+'-'+#pageRequest.pageSize")
    @Override
    public Result userList(PageRequest pageRequest) {
        Result r = userClient.adminListPage(pageRequest);
        log.info("UserServiceImpl.userList业务结束，结果:{}",r);
        return r;
    }

    /**
     * 根据id删除用户数据
     *
     * @param cartListRequest
     * @return
     */
    @CacheEvict(value = "list.user",allEntries = true) //清空缓存
    @Override
    public Result userRemove(CartListRequest cartListRequest) {
        Result result = userClient.adminRemove(cartListRequest);
        log.info("UserServiceImpl.userRemove业务结束，结果:{}",result);
        return result;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "list.user",allEntries = true) //清空缓存
    @Override
    public Result userUpdate(User user) {
        Result result = userClient.adminUpdate(user);
        log.info("UserServiceImpl.update业务结束，结果:{}",result);
        return result;
    }

    /**
     * 用户添加功能
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "list.user",allEntries = true) //清空缓存
    @Override
    public Result userSave(User user) {
        Result result = userClient.adminSave(user);
        log.info("UserServiceImpl.save业务结束，结果:{}",result);
        return result;
    }
}
