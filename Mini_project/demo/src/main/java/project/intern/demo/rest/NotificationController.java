package project.intern.demo.rest;


import org.springframework.web.bind.annotation.*;
import project.intern.demo.dto.request.notification.NotificationCreateRequest;
import project.intern.demo.dto.request.notification.NotificationUpdateRequest;
import project.intern.demo.dto.response.ApiResponse;
import project.intern.demo.dto.response.NotificationResponse;
import project.intern.demo.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ApiResponse<List<NotificationResponse>> getAllNotifications()
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(notificationService.getAllNotifications());
        return apiResponse;
    }
    @GetMapping("/{id}")
    public ApiResponse<NotificationResponse> getNotificationById(@PathVariable int id)
    {
        NotificationResponse notification = notificationService.getNotificationById(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(notification);
        return apiResponse;
    }
    @GetMapping("/user/{userId}")
    public ApiResponse<NotificationResponse> getByUser_Id(@PathVariable int userId)
    {
        List<NotificationResponse> responses = notificationService.getByUserId(userId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(responses);

        return apiResponse;
    }
    @PutMapping("/{id}")
    public ApiResponse<NotificationResponse> updateNotification(@PathVariable int id,@RequestBody NotificationUpdateRequest notificationUpdateRequest)
    {
        NotificationResponse notification = notificationService.getNotificationById(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(notificationService.updateNotification(id,notificationUpdateRequest));
        return apiResponse;
    }
    @PostMapping
    public ApiResponse<NotificationResponse> addNotification(@RequestBody  NotificationCreateRequest notificationCreateRequest)
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(notificationService.addNotification(notificationCreateRequest));
        return apiResponse;
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteNotificationById(@PathVariable int id)
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(notificationService.deleteNotificationById(id));
        return apiResponse;
    }
}
