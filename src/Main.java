import model.Product;
import repository.InMemoryProductRepository;
import service.CartService;
import service.OrderService;
import service.ProductService;
import service.RecommendationService;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // Инициализация данных
        InMemoryProductRepository productRepo = new InMemoryProductRepository();
        productRepo.addProduct(new Product(1, "Ноутбук", "Dell", 999.99, 10));
        productRepo.addProduct(new Product(2, "Мышь", "Logitech", 25.50, 50));
        productRepo.addProduct(new Product(3, "Клавиатура", "Corsair", 89.99, 20));

        // Оценка товара для рекомендаций
        Product laptop = productRepo.findById(1).get();
        laptop.addRating(5.0);
        laptop.addRating(4.5);

        // Сервисы
        ProductService productService = new ProductService(productRepo);
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();
        RecommendationService recommendationService = new RecommendationService(productService);

        // UI
        ConsoleUI ui = new ConsoleUI(productService, cartService, orderService, recommendationService);
        ui.start();
    }
}
