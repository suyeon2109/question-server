package com.question.memo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.question.memo.dto.fcm.PushAlarm;
import com.question.memo.service.FcmNotificationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SchedulerConfig {
	private final FcmNotificationService fcmNotificationService;

	@Value("${schedule.morning.use}")
	private boolean useMorningScheduler;

	@Value("${schedule.evening.use}")
	private boolean useEveningScheduler;

	@Value("${schedule.dawn.use}")
	private boolean useDawnScheduler;

	@Scheduled(cron = "${schedule.morning.cron}")
	public void runMorningScheduler() {
		if (useMorningScheduler) {
			fcmNotificationService.sendNotificationByToken(PushAlarm.MORNING);
		}
	}

	@Scheduled(cron = "${schedule.evening.cron}")
	public void runEveningScheduler() {
		if (useEveningScheduler) {
			fcmNotificationService.sendNotificationByToken(PushAlarm.EVENING);
		}
	}

	@Scheduled(cron = "${schedule.dawn.cron}")
	public void runDawnScheduler() {
		if (useDawnScheduler) {
			fcmNotificationService.sendNotificationByToken(PushAlarm.DAWN);
		}
	}
}
