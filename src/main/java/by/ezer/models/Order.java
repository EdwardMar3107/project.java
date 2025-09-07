package by.ezer.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import jakarta.persistence.Entity;

public class Order {

    private Long id;
    private Long userId;
    private LocalDate date;
    private String status;
    private List<Product> products;

    public Order (Long userId, LocalDate date, String status, List<Product> products) {
        this.userId = userId;
        this.date = date;
        this.status = status;
        this.products = products != null ? new ArrayList<>(products) : new ArrayList<>();
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

    public List<Product> getProducts() {
        return products;
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

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public void setId(long id) {
        this.id = id;
    }
}
