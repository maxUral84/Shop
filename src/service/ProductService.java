package service;

import filter.FilterByPrice;
import model.Product;
import repository.InMemoryProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {
    private final InMemoryProductRepository repository;

    public ProductService(InMemoryProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public List<Product> filterByKeyword(String keyword) {
        return repository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        p.getBrand().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> filterByPrice(double min, double max) {
        return new FilterByPrice(min, max).filter(repository.findAll());
    }

    public Optional<Product> findProductById(int id) {
        return repository.findById(id);
    }

    public void updateProduct(Product product) {
        repository.update(product);
    }
}