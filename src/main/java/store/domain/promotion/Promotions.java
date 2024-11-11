package store.domain.promotion;

import java.util.Map;

public class Promotions {
    private final Map<String, Promotion> products;

    public Promotions(Map<String, Promotion> products) {
        this.products = products;
    }

    public Promotion findPromotion(String promotion) {
        return products.getOrDefault(promotion, PromotionsFactory.createNonPromotion());
    }
}