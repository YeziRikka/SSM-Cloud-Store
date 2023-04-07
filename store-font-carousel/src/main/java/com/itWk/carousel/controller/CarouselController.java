package com.itWk.carousel.controller;


import com.itWk.Utils.Result;
import com.itWk.carousel.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 轮播图控制类
 */

@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    /**
     * 查询轮播图数据，展示优秀级最高的几条
     * @return
     */
    @PostMapping("/list")
    public Result list(){

        return carouselService.list();
    }
}
