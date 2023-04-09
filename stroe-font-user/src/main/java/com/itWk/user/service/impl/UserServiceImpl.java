package com.itWk.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itWk.POJO.User;
import com.itWk.Utils.MD5Util;
import com.itWk.Utils.Result;
import com.itWk.constants.UserContants;
import com.itWk.param.PageRequest;
import com.itWk.param.UserCheckRequest;
import com.itWk.param.UserLoginRequest;
import com.itWk.user.mapper.UserMapper;
import com.itWk.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务实现类
 */
@Slf4j //日志输出
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result check(UserCheckRequest userCheckRequest) {

        //封装参数
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userCheckRequest.getUserName());//判断username参数是否相同

        //查询数据库
        /**
         * 根据参数查询存在的数量，=0说明可用， >0说明已经存在 不可用
         */
        Long total = userMapper.selectCount(queryWrapper);

        //查询结果处理
        if (total == 0){
            //数据库中不存在,可用
            log.info("UserServiceImpl.check业务结束，结果:{}","账号可用使用");
            return Result.ok("该账号尚未注册！");
        }

        log.info("UserServiceImpl.check业务结束，结果:{}","账号已经存在");

        return Result.fail("该账号已经存在！请登录");

    }

    @Override
    public Result register(User user) {

        //检查账号是否可用
        //封装参数
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());//判断username参数是否相同
        //数据库查询
        Long total = userMapper.selectCount(queryWrapper);
        //查询结果处理
        if (total > 0){
            //数据库中不存在,可用
            log.info("UserServiceImpl.register业务结束，结果:{}","账号存在");
            return Result.ok("该账号以存在，请登录");
        }
        //密码加密 使用MD5
        String newPwd = MD5Util.encode(user.getPassword() + UserContants.UESR_SLAT);
        user.setPassword(newPwd);
        //执行数据库插入
        int row = userMapper.insert(user); //得到影响行数
        if (row == 0){
            log.info("UserServiceImpl.register业务结束，结果:{}","注册失败");
            return Result.fail("注册失败，请重试");
        }
        return Result.ok("注册成功！");
    }

    /**
     * 密码的加解密处理
     * @param userLoginRequest
     * @return
     */
    @Override
    public Result login(UserLoginRequest userLoginRequest) {
        //密码处理
        String newPwd = MD5Util.encode(userLoginRequest.getPassword() + UserContants.UESR_SLAT);
        //数据库查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userLoginRequest.getUserName());
        //用加密后的密码去对比
        queryWrapper.eq("password", newPwd);
        User user = userMapper.selectOne(queryWrapper);
        //结果处理
        if (user == null){
            log.info("UserServiceImpl.login业务结束，结果:{}","账号或密码错误");
            return Result.fail("账号或密码错误");
        }
        log.info("UserServiceImpl.login业务结束，结果:{}","登陆成功");
        user.setPassword(null); //设置不返回json
        return Result.ok("登录成功",user);
    }

    /**
     * 后台管理服务
     * 查询所有用户数据
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Result listPage(PageRequest pageRequest) {
        IPage<User> page = new Page<>(pageRequest.getCurrentPage(),pageRequest.getPageSize());
        page = userMapper.selectPage(page, null);
        List<User> records = page.getRecords();
        long total = page.getTotal();
        return Result.ok("用户管理查询成功",records,total);
    }

    /**
     * 根据用户id删除用户数据
     *
     * @param userId
     * @return
     */
    @Override
    public Result remove(Integer userId) {
        int i = userMapper.deleteById(userId);
        log.info("UserServiceImpl.remove业务结束，结果:{}","删除成功",i);
        return Result.ok("用户数据删除成功");
    }

    /**
     * 用户修改功能
     *
     * @param user
     * @return
     */
    @Override
    public Result update(User user) {
        /**
         * 账号和id不可以修改
         * 判断密码是否和已有的相同，相同不可以修改
         * 新密码加密后更新
         * 修改用户信息
         */
        //判断密码是否与原来的相同
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId());
        queryWrapper.eq("password", user.getPassword());
        Long aLong = userMapper.selectCount(queryWrapper);
        if (aLong == 0){
            //为0则说明与旧密码不相同
            //加密密码
            user.setPassword(MD5Util.encode(user.getPassword()+UserContants.UESR_SLAT));
        }
        int rows = userMapper.updateById(user);
        log.info("UserServiceImpl.update业务结束，结果:{}","修改成功",rows);
        return Result.ok("用户信息修改成功");
    }

    /**
     * 用户新增
     * TODO：和注册一样
     * @param user
     * @return
     */
    @Override
    public Result save(User user) {
        //检查账号是否可用
        //封装参数
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());//判断username参数是否相同
        //数据库查询
        Long total = userMapper.selectCount(queryWrapper);
        //查询结果处理
        if (total > 0){
            //数据库中不存在,可用
            log.info("UserServiceImpl.register业务结束，结果:{}","账号存在");
            return Result.ok("该账号以存在，不可添加");
        }
        //密码加密 使用MD5
        String newPwd = MD5Util.encode(user.getPassword() + UserContants.UESR_SLAT);
        user.setPassword(newPwd);
        //执行数据库插入
        int row = userMapper.insert(user); //得到影响行数
        if (row == 0){
            log.info("UserServiceImpl.register业务结束，结果:{}","添加失败");
            return Result.fail("添加失败，请重试");
        }
        return Result.ok("添加成功！");

    }
}
