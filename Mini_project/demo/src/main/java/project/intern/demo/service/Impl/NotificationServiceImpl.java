package project.intern.demo.service.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import project.intern.demo.dto.request.notification.NotificationCreateRequest;
import project.intern.demo.dto.request.notification.NotificationUpdateRequest;
import project.intern.demo.dto.response.BorrowedRecordResponse;
import project.intern.demo.dto.response.NotificationResponse;
import project.intern.demo.entity.BorrowedRecord;
import project.intern.demo.entity.Notification;
import project.intern.demo.entity.User;
import project.intern.demo.exception.AppException;
import project.intern.demo.exception.ErrorCode;
import project.intern.demo.repository.BorrowedRecordRepository;
import project.intern.demo.repository.NotificationRepository;
import project.intern.demo.repository.UserRepository;
import project.intern.demo.service.NotificationService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final BorrowedRecordRepository borrowedRecordRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository, BorrowedRecordRepository borrowedRecordRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.borrowedRecordRepository = borrowedRecordRepository;
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreateAt(),
                notification.getUser().getUsername(),
                notification.getUser().getId()
        );
    }


    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll().stream().map(this::mapToResponse)
                .toList();
    }

    @Override
    @PostAuthorize("returnObject.user_id == authentication.tokenAttributes['userId'] or hasAuthority('SCOPE_ADMIN')")
    public NotificationResponse getNotificationById(int id) {
        return mapToResponse(notificationRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED)));
    }

    @Override
    @Transactional
    @PreAuthorize("#notificationUpdateRequest.user_id == authentication.tokenAttributes['userId'] or hasAuthority('SCOPE_ADMIN')")
    public NotificationResponse updateNotification(int id, NotificationUpdateRequest notificationUpdateRequest) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED));
        User user = userRepository.findById(notificationUpdateRequest.getUser_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        notification.setRead(notificationUpdateRequest.isRead());
        notification.setMessage(notificationUpdateRequest.getMessage());
        notification.setCreateAt(notificationUpdateRequest.getCreateAt());
        notification.setUser(user);

        return mapToResponse(notificationRepository.saveAndFlush(notification));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public NotificationResponse addNotification(NotificationCreateRequest notificationCreateRequest) {
        User user = userRepository.findById(notificationCreateRequest.getUser_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Notification notification = new Notification();
        notification.setMessage(notificationCreateRequest.getMessage());
        notification.setRead(notificationCreateRequest.isRead());
        notification.setCreateAt(notificationCreateRequest.getCreateAt());
        notification.setUser(user);
        return mapToResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String deleteNotificationById(int id) {
        if (!notificationRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED);
        }
        notificationRepository.deleteById(id);
        return "Notification has been deleted";
    }

    @Override
    @PreAuthorize("#id == authentication.tokenAttributes['userId'] or hasAuthority('SCOPE_ADMIN')")
    public List<NotificationResponse> getByUserId(int id) {
        List<Notification> notification = notificationRepository.findByUser_Id(id);
        if (notification.isEmpty()) {
            throw new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED);
        }
        return notification.stream().map(this::mapToResponse).toList();
    }

    @Override
    public void generateDueNotification() {
        LocalDate today = LocalDate.now();

        // Lấy tất cả BorrowedRecord đang BORROWED
        List<BorrowedRecord> borrowedRecords = borrowedRecordRepository.findAllByStatus("BORROWED");

        for (BorrowedRecord r : borrowedRecords) {
            long daysLeft = ChronoUnit.DAYS.between(today, r.getDueDate());
            String bookName = r.getBook().getTitle();
            User user = r.getUser();
            String userName = user.getUsername();

            String msg = null;
            if (daysLeft == 2) {
                msg = "Người dùng " + userName + " còn 2 ngày nữa đến hạn trả sách " + bookName;
            } else if (daysLeft == 1) {
                msg = "Người dùng " + userName + " còn 1 ngày nữa đến hạn trả sách " + bookName;
            } else if (daysLeft < 0) {
                msg = "Người dùng " + userName + " có sách " + bookName + " đã hết hạn từ " + r.getDueDate();
            }

            if (msg != null) {
                // Kiểm tra xem Notification này đã tồn tại hôm nay chưa
                boolean exists = notificationRepository.existsByUserAndMessageAndCreateAt(user, msg, today);
                if (!exists) {
                    Notification n = new Notification();
                    n.setUser(user);
                    n.setMessage(msg);
                    n.setRead(false);
                    n.setCreateAt(today);
                    notificationRepository.save(n);
                }
            }
        }
    }

}
