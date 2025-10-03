package project.intern.demo.dto.request.notification;

import jakarta.persistence.*;
import project.intern.demo.entity.User;

import java.time.LocalDate;


public class NotificationCreateRequest {
    private String message;
    private boolean read;
    private LocalDate createAt;
    private int user_id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
