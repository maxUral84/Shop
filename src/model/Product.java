package model;

public class Product {
    private final int id;
    private final String name;
    private final String brand;
    private final double price;
    private int stock;
    private double rating;
    private int ratingCount;

    public Product(int id, String name, String brand, double price, int stock) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.rating = 0.0;
        this.ratingCount = 0;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public double getRating() { return rating; }

    public void decreaseStock(int quantity) {
        if (quantity > stock) throw new IllegalArgumentException("Недостаточно товара");
        stock -= quantity;
    }

    public void increaseStock(int quantity) {
        stock += quantity;
    }

    public void addRating(double score) {
        double total = rating * ratingCount + score;
        ratingCount++;
        rating = total / ratingCount;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | %s | $%.2f | Остаток: %d | Рейтинг: %.1f",
                id, name, brand, price, stock, rating);
    }
}