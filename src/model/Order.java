package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    public enum Status {
        PROCESSING, SHIPPED, DELIVERED, RETURNED
    }

    private final int id;
    private final User user;
    private final Map<Product, Integer> items;
    private final LocalDateTime orderDate;
    private Status status;

    public Order(int id, User user, Map<Product, Integer> items) {
        this.id = id;
        this.user = user;
        this.items = new HashMap<>(items);
        this.orderDate = LocalDateTime.now();
        this.status = Status.PROCESSING;
    }

    // Геттеры
    public int getId() { return id; }
    public User getUser() { return user; }
    public Map<Product, Integer> getItems() { return new HashMap<>(items); }
    public LocalDateTime getOrderDate() { return orderDate; }
    public Status getStatus() { return status; }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getTotal() {
        return items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

    @Override
    public String toString() {
        return String.format("Заказ #%d | %s | Статус: %s | Сумма: $%.2f",
                id, orderDate, status, getTotal());
    }
}