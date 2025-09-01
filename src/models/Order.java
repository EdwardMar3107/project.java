package models;

import java.time.LocalDate;

public class Order {

    private Long id;
    private Long userId;
    private LocalDate date;
    private String status;

    public Order (Long userId, LocalDate date, String status) {
        this.userId = userId;
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
