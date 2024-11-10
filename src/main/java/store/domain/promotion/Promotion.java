package store.domain.promotion;

public class Promotion {
    private String name;
    private int buy;
    private int get;
    private PromotionActive promotionActive;

    public Promotion(String name, int buy, int get, PromotionActive promotionActive) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.promotionActive = promotionActive;
    }
}