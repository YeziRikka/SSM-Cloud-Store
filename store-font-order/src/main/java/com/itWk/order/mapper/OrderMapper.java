package com.itWk.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itWk.POJO.Order;
import com.itWk.vo.AdminOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单的数据库接口
 */
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 查询后台管理的数据方法
     * @param offset
     * @param pageSize
     * @return
     */
    List<AdminOrderVo> selectAdminOrder(@Param("offset") int offset, @Param("pageSize") int pageSize);
}
