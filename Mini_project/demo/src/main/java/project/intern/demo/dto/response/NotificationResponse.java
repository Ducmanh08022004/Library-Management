package project.intern.demo.dto.response;

import java.time.LocalDate;

public class NotificationResponse {
    private int id;
    private String message;
    private boolean read;
    private LocalDate createAt;
    private String username;
    private int user_id;


    public NotificationResponse(int id, String message, boolean read, LocalDate createAt, String username, int userId) {
        this.id = id;
        this.message = message;
        this.read = read;
        this.createAt = createAt;
        this.username = username;
        this.user_id = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
