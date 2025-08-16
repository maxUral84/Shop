package service;

import model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class RecommendationService {
    private final ProductService productService;

    public RecommendationService(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getTopRatedProducts(int limit) {
        return productService.getAllProducts().stream()
                .sorted((a, b) -> Double.compare(b.getRating(), a.getRating()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
