package repository;

import model.Product;

import java.util.*;

public class InMemoryProductRepository {
    private final Map<Integer, Product> products = new HashMap<>();
    private int nextId = 1;

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Optional<Product> findById(int id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public List<Product> findByBrand(String brand) {
        return products.values().stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .toList();
    }

    public void update(Product product) {
        products.put(product.getId(), product);
    }
}