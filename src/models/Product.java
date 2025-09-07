package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product {

    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean isAvailable;
    private LocalDate createdAt;

    public Product (Long id, String name, BigDecimal price, Boolean isAvailable, LocalDate createdAt) {
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", createdAt=" + createdAt +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }
}
