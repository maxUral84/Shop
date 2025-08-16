package ui;

import model.Cart;
import model.Order;
import model.Product;
import model.User;
import service.CartService;
import service.OrderService;
import service.ProductService;
import service.RecommendationService;

import java.util.*;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;
    private final RecommendationService recommendationService;
    private User currentUser;

    public ConsoleUI(ProductService productService, CartService cartService,
                     OrderService orderService, RecommendationService recommendationService) {
        this.productService = productService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.recommendationService = recommendationService;
    }

    public void start() {
        System.out.println("Добро пожаловать в Магазин!");
        login();

        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка

            switch (choice) {
                case 1 -> viewProducts();
                case 2 -> searchProducts();
                case 3 -> addToCart();
                case 4 -> viewCart();
                case 5 -> placeOrder();
                case 6 -> viewOrders();
                case 7 -> trackOrder();
                case 8 -> returnOrder();
                case 9 -> viewRecommendations();
                case 0 -> {
                    System.out.println("До свидания!");
                    return;
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private void login() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        currentUser = new User(new Random().nextInt(1000), name, email);
        System.out.println("Добро пожаловать, " + name + "!\n");
    }

    private void showMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Просмотр товаров");
        System.out.println("2. Поиск товара");
        System.out.println("3. Добавить в корзину");
        System.out.println("4. Просмотр корзины");
        System.out.println("5. Оформить заказ");
        System.out.println("6. Мои заказы");
        System.out.println("7. Отслеживание заказа");
        System.out.println("8. Возврат заказа");
        System.out.println("9. Рекомендации");
        System.out.println("0. Выход");
        System.out.print("Выберите: ");
    }

    private void viewProducts() {
        List<Product> products = productService.getAllProducts();
        products.forEach(System.out::println);
    }

    private void searchProducts() {
        System.out.print("Введите ключевое слово: ");
        String keyword = scanner.nextLine();
        List<Product> results = productService.filterByKeyword(keyword);
        if (results.isEmpty()) {
            System.out.println("Товары не найдены.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void addToCart() {
        System.out.print("Введите ID товара: ");
        int id = scanner.nextInt();
        System.out.print("Количество: ");
        int qty = scanner.nextInt();
        scanner.nextLine();

        Optional<Product> productOpt = productService.findProductById(id);
        if (productOpt.isPresent()) {
            cartService.addToCart(productOpt.get(), qty);
            System.out.println("Добавлено в корзину!");
        } else {
            System.out.println("Товар не найден.");
        }
    }

    private void viewCart() {
        Cart cart = cartService.getCart();
        if (cart.isEmpty()) {
            System.out.println("Корзина пуста.");
            return;
        }
        cart.getItems().forEach((p, q) -> System.out.println(p + " | Количество: " + q));
        System.out.printf("Итого: $%.2f\n", cart.getTotalPrice());
    }

    private void placeOrder() {
        Cart cart = cartService.getCart();
        if (cart.isEmpty()) {
            System.out.println("Корзина пуста.");
            return;
        }

        Order order = orderService.createOrder(currentUser, cart.getItems());
        cart.getItems().forEach((product, qty) -> product.decreaseStock(qty));
        System.out.println("Заказ оформлен! Номер: " + order.getId());
        cartService.clearCart();
    }

    private void viewOrders() {
        List<Order> orders = orderService.getOrdersByUser(currentUser);
        if (orders.isEmpty()) {
            System.out.println("Заказов нет.");
        } else {
            orders.forEach(System.out::println);
        }
    }

    private void trackOrder() {
        System.out.print("Введите ID заказа: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        orderService.trackOrder(id);
    }

    private void returnOrder() {
        System.out.print("Введите ID заказа для возврата: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        orderService.returnOrder(id);
        System.out.println("Заказ возвращён.");
    }

    private void viewRecommendations() {
        List<Product> recs = recommendationService.getTopRatedProducts(5);
        System.out.println("Рекомендуем:");
        recs.forEach(System.out::println);
    }
}