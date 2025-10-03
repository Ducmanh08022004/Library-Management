package project.intern.demo.dto.response;

import java.time.LocalDate;

public class BorrowedRecordResponse {
    private int id;
    private LocalDate borrow_date;
    private LocalDate return_date;
    private LocalDate due_date;
    private String status;
    private int user_id;
    private int book_id;

    public BorrowedRecordResponse(int id, LocalDate borrow_date, LocalDate return_date, LocalDate due_date, String status, int user_id, int book_id) {
        this.id = id;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
        this.due_date = due_date;
        this.status = status;
        this.user_id = user_id;
        this.book_id = book_id;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(LocalDate borrow_date) {
        this.borrow_date = borrow_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
}
