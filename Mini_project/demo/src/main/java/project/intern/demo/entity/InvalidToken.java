package project.intern.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "invalidToken")
public class InvalidToken {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "expiryTime")
    private Date expiryTime;

    public InvalidToken() {
    }

    public InvalidToken(String id, Date expiryTime) {
        this.id = id;
        this.expiryTime = expiryTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }
}
