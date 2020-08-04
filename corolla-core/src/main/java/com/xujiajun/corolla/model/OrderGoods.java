package com.xujiajun.corolla.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Setter
@Getter
@ToString
public class OrderGoods implements Serializable {

    private static final long serialVersionUID = 710243655954332255L;

    private Long id;

    /**
     * 用户id
     */
    private Long orderId;

    /**
     * 订单id
     */
    private Long goodsId;

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
