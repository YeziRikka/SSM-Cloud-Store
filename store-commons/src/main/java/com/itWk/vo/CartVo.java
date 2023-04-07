package com.itWk.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itWk.POJO.Cart;
import com.itWk.POJO.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 知识点记录：
 * 一、PO:persistant object 持久对象
 * 可以看成是与数据库中的表相映射的java对象。使用Hibernate来生成PO是不错的选择。
 *
 * 二、VO:value object值对象。
 * 通常用于业务层之间的数据传递，和PO一样也是仅仅包含数据而已。但应是抽象出的业务对象
 * 可以和表对应,也可以不,这根据业务的需要.
 *
 * vo:value object,值对象
 * 一般在java中用的多的是pojo:plain oriented java object
 * 原始java对象，pojo一般和数据库中的表是一一对应的。
 * vo一般是来做值的存储与传递。
 * TODO：购物车添加返回的对象
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartVo implements Serializable {

    private Integer id;  //购物车id
    private Integer productID;  //商品id
    private String  productName; //商品名称
    private String  productImg; //商品显示图片
    private Double price;  //商城价格
    private Integer num;  //商品购买数量
    private Integer maxNum; //商品限购数量
    private Boolean check = false; //是否勾选

    public CartVo(Product product, Cart cart) {
        this.id = cart.getId();
        this.productID = product.getProductId();
        this.productName = product.getProductName();
        this.productImg = product.getProductPicture();
        this.price = product.getProductSellingPrice();
        this.num = cart.getNum();
        this.maxNum = product.getProductNum();
        this.check = false;
    }

}
