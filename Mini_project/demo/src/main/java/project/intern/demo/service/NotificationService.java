package project.intern.demo.service;

import org.springframework.stereotype.Service;
import project.intern.demo.dto.request.notification.NotificationCreateRequest;
import project.intern.demo.dto.request.notification.NotificationUpdateRequest;
import project.intern.demo.dto.response.NotificationResponse;
import project.intern.demo.entity.Notification;
import project.intern.demo.rest.NotificationController;


import java.util.List;

public interface NotificationService {
    public List<NotificationResponse> getAllNotifications();
    public NotificationResponse getNotificationById(int id);
    public NotificationResponse updateNotification(int id, NotificationUpdateRequest notificationUpdateRequest);
    public NotificationResponse addNotification(NotificationCreateRequest notificationCreateRequest);
    public String deleteNotificationById(int id);
    public List<NotificationResponse> getByUserId(int id);
    public void generateDueNotification();
}
