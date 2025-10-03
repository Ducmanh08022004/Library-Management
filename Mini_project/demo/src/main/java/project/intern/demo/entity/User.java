package project.intern.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "fullname",length = 50)
    private String fullName;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "role", length = 20)
    private String role;

    @Column(name = "email", length = 50)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BorrowedRecord> borrowedRecordList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notificationList;
    public User() {
    }

    public User(int id, String username, String fullname, String password, String role, String email, List<BorrowedRecord> borrowedRecordList, List<Notification> notificationList) {
        this.id = id;
        this.username = username;
        this.fullName = fullname;
        this.password = password;
        this.role = role;
        this.email = email;
        this.borrowedRecordList = borrowedRecordList;
        this.notificationList = notificationList;
    }

    public List<BorrowedRecord> getBorrowedRecordList() {
        return borrowedRecordList;
    }

    public void setBorrowedRecordList(List<BorrowedRecord> borrowedRecordList) {
        this.borrowedRecordList = borrowedRecordList;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
