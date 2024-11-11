package store.domain.stock;

import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionResult;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public Product(Product product, int quantity) {
        this.name = product.name();
        this.price = product.price();
        this.quantity = quantity;
        this.promotion = product.promotion();
    }

    public String name() {
        return name;
    }

    public int price() {
        return price;
    }

    public int quantity() {
        return quantity;
    }

    public String promotion() {
        return promotion;
    }

    public void purchase(int quantity) {
        this.quantity -= quantity;
    }

    public PromotionResult checkPromotion(int requestedQuantity, Promotion promotion) {
        return promotion.applyPromotion(requestedQuantity, quantity);
    }
}
