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
public class User implements Serializable {

    private static final long serialVersionUID = 1902494144266794482L;

    private Long id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户账户余额
     */
    private BigDecimal account;

    /**
     * 用户的积分
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
