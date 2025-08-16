package filter;

import model.Product;

import java.util.List;

public class FilterByPrice implements ProductFilter {
    private final double minPrice;
    private final double maxPrice;

    public FilterByPrice(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .toList();
    }
}