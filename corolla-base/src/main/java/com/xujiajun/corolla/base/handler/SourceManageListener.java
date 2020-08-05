package com.xujiajun.corolla.base.handler;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Slf4j
@WebListener(value = "sourceManagerListener")
public class SourceManageListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("全局资源管理监听器启动...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("全局资源管理监听器销毁...");
	}
}
