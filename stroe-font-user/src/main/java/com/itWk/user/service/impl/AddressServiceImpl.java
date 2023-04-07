package com.itWk.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itWk.POJO.Address;
import com.itWk.Utils.Result;
import com.itWk.user.mapper.AddressMapper;
import com.itWk.user.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地址实现类
 */
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Result list(Integer userId) {
        //封装查询参数
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Address> addressList = addressMapper.selectList(queryWrapper);
        //结果封装
        Result ok = Result.ok("查询成功", addressList);
        log.info("AddressServiceImpl.list业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 插入地址数据，返回新的地址数据集合
     *
     * @param address
     * @return
     */
    @Override
    public Result save(Address address) {
        //插入数据
        int rows = addressMapper.insert(address);
        //判断是否成功
        if (rows == 0){
            log.info("AddressServiceImpl.save业务结束，结果:{}","地址失败!");
            return Result.fail("插入地址失败!");
        }
        //复用查询业务
        return list(address.getUserId());
    }

    /**
     * 根据id删除对应地址
     *
     * @param id
     * @return
     */
    @Override
    public Result remove(Integer id) {
        //查询
        int rows = addressMapper.deleteById(id);
        if (rows == 0){
            log.info("AddressServiceImpl.remove业务结束，结果:{}","地址删除失败");
            return Result.fail("地址删除失败!");
        }
        log.info("AddressServiceImpl.remove业务结束，结果:{}","地址删除成功!");
        return Result.ok("地址删除成功!");
    }


}
