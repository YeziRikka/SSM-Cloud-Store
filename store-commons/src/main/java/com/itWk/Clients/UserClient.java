package com.itWk.Clients;

import com.itWk.POJO.User;
import com.itWk.Utils.Result;
import com.itWk.param.CartListRequest;
import com.itWk.param.PageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户客户端
 */
@FeignClient("user-service")
public interface UserClient {

    @PostMapping("/user/admin/list")
    Result adminListPage(@RequestBody PageRequest pageRequest);

    @PostMapping("/user/admin/remove")
    Result adminRemove(@RequestBody CartListRequest cartListRequest);

    @PostMapping("/user/admin/update")
    Result adminUpdate(@RequestBody User user);

    @PostMapping("/user/admin/save")
    Result adminSave(@RequestBody User user);
}
