package service;

import model.Cart;
import model.Product;

public class CartService {
    private final Cart cart = new Cart();

    public void addToCart(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Недостаточно товара: " + product.getName());
        }
        cart.addProduct(product, quantity);
    }

    public Cart getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}