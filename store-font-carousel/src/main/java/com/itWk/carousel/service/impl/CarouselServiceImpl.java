package com.itWk.carousel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itWk.POJO.Carousel;
import com.itWk.Utils.Result;
import com.itWk.carousel.mapper.CarouselMapper;
import com.itWk.carousel.service.CarouselService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;
    /**
     * 查询优先级最高的轮播图数据
     *
     * @return
     */
    @Cacheable(value = "list.carousel",key = "#root.methodName")
    @Override
    public Result list() {
        //数据库查询
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        //按照优先级字段名从大到小进行排序
        queryWrapper.orderByDesc("priority");
        //查询全部数据 把查询条件的wrapper传进去
        List<Carousel> list = carouselMapper.selectList(queryWrapper);
        //切割条数
        List<Carousel> collect = list.stream().limit(6).collect(Collectors.toList());
        //结果处理，封装并返回
        Result ok = Result.ok(collect);
        log.info("CarouselServiceImpl.list业务结束，结果:{}",ok);
        return ok;
    }
}
