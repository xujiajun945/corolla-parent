package com.xujiajun.corolla.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Setter
@Getter
@ToString
public class Goods implements Serializable {

    private static final long serialVersionUID = 1121238997974256922L;

    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存数量
     */
    private Long stock;

    /**
     * 商品的积分
     */
    private BigDecimal score;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
