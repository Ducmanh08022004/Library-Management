package project.intern.demo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNAUTHENTICATED(1003,"Unauthencited",HttpStatus.UNAUTHORIZED),
    UNDEFINED_EXCEPTION(9999,"Undefined_exception",HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002,"User existed",HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1001,"User not found",HttpStatus.NOT_FOUND),
    BOOK_NOT_FOUND(2001,"Book not found",HttpStatus.NOT_FOUND),
    BORROWEDRECORD_NOT_EXISTED(3001,"BorrowedRecord not existed",HttpStatus.NOT_FOUND),
    NOTIFICATION_NOT_EXISTED(4001,"Notification not existed",HttpStatus.NOT_FOUND),
    FORBIDEN(1004,"FORBIDEN",HttpStatus.FORBIDDEN),
    NOT_RETURNED(3002,"The book has not been returned",HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message,HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private int code;
    private String message;
    private HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
