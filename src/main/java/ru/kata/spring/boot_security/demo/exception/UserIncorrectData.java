package ru.kata.spring.boot_security.demo.exception;

public class UserIncorrectData {
    private String info;
    private String httpStatus;

    public UserIncorrectData() {}

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String status) {
        this.httpStatus = status;
    }
}
