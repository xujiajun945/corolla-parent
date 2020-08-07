package com.xujiajun.corolla.base.task;

import com.xujiajun.corolla.base.compose.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Component
@EnableScheduling
public class MqAsyncTask {

	@Autowired
	private ScheduleService scheduleService;

	@Async
	@Scheduled(cron = "0/30 * * * * *")
	public void clearSchedule() {
		scheduleService.doSchedule();
	}

}
