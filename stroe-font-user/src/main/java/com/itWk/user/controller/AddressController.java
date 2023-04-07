package com.itWk.user.controller;

import com.itWk.POJO.Address;
import com.itWk.Utils.Result;
import com.itWk.param.AddressListRequest;
import com.itWk.param.AddressRemoveRequest;
import com.itWk.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 地址控制类
 */
@RestController
@RequestMapping("/user/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping("/list")
    public Result list(@RequestBody @Validated AddressListRequest addressListRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Result.fail("参数异常，查询失败");
        }
        return addressService.list(addressListRequest.getUserId());
    }

    @PostMapping("/save")
    public Result save(@RequestBody @Validated Address address,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Result.fail("参数异常，保存失败");
        }
        return addressService.save(address);
    }

    @DeleteMapping("/remove")
    public Result remove(@RequestBody @Validated AddressRemoveRequest addressRemoveRequest,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Result.fail("参数异常，删除失败");
        }
        return addressService.remove(addressRemoveRequest.getId());

    }
}
