package filter;

import model.Product;

import java.util.List;

public interface ProductFilter {
    List<Product> filter(List<Product> products);
}