package project.intern.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.intern.demo.service.NotificationService;

@Service
public class ScheduledTasks {

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 8 * * ?") // mỗi ngày 8h sáng
    public void generateNotifications() {
        notificationService.generateDueNotification();
    }
}

