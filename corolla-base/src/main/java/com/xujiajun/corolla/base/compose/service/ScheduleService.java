package com.xujiajun.corolla.base.compose.service;

import com.xujiajun.corolla.model.OrderMsg;

/**
 * @author xujiajun
 * @since 2020/8/6
 */
public interface ScheduleService {

	/**
	 * 执行任务
	 */
	void doSchedule();

	/**
	 * 发送消息
	 *
	 * @param orderMsg 消息
	 */
	void sendMessage(OrderMsg orderMsg);
}
