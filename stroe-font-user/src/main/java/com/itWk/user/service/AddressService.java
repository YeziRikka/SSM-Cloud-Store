package com.itWk.user.service;

import com.itWk.POJO.Address;
import com.itWk.Utils.Result;

public interface AddressService {
    /**
     * 根据用户ID查询地址
     * @param userId
     * @return
     */
    Result list(Integer userId);

    /**
     * 插入地址数据，返回新的地址数据集合
     * @param address
     * @return
     */
    Result save(Address address);

    /**
     * 根据id删除对应地址
     * @param id
     * @return
     */
    Result remove(Integer id);
}
