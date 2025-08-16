package service;

import model.Order;
import model.Product;
import model.User;

import java.util.*;

public class OrderService {
    private final List<Order> orders = new ArrayList<>();
    private int nextOrderId = 1;

    public Order createOrder(User user, Map<Product, Integer> items) {
        Order order = new Order(nextOrderId++, user, items);
        orders.add(order);
        return order;
    }

    public List<Order> getOrdersByUser(User user) {
        return orders.stream()
                .filter(o -> o.getUser().getId() == user.getId())
                .toList();
    }

    public Optional<Order> findOrderById(int orderId) {
        return orders.stream()
                .filter(o -> o.getId() == orderId)
                .findFirst();
    }

    public void returnOrder(int orderId) {
        findOrderById(orderId).ifPresent(order -> {
            Order.Status status = order.getStatus();
            if (status == Order.Status.PROCESSING) {
                throw new IllegalStateException("Заказ ещё обрабатывается, возврат невозможен");
            }
            if (status == Order.Status.RETURNED) {
                throw new IllegalStateException("Заказ уже возвращён");
            }
            order.setStatus(Order.Status.RETURNED);
            System.out.println("Заказ #" + orderId + " успешно возвращён.");
        });
    }

    public void trackOrder(int orderId) {
        findOrderById(orderId).ifPresentOrElse(
                order -> System.out.println("Статус заказа: " + order.getStatus()),
                () -> System.out.println("Заказ не найден")
        );
    }
}