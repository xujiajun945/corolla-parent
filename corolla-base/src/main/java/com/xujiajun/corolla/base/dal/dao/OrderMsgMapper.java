package com.xujiajun.corolla.base.dal.dao;

import com.xujiajun.corolla.message.OrderMessage;
import com.xujiajun.corolla.model.OrderMsg;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
public interface OrderMsgMapper {

	/**
	 * 添加
	 *
	 * @param orderMsg 订单消息
	 */
	void insert(OrderMsg orderMsg);

	/**
	 * 根据id删除
	 *
	 * @param id id
	 */
	void removeById(Long id);

	/**
	 * 根据主键更新
	 *
	 * @param orderMsg 订单消息
	 */
	void updateById(OrderMsg orderMsg);

	/**
	 * 查询全部的记录
	 *
	 * @return 全部记录
	 */
	List<OrderMsg> listAllEnabled();

}
