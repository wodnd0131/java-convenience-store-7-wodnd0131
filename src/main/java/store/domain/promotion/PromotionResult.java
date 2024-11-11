package store.domain.promotion;

public class PromotionResult {
    private final PromotionStatus status;
    private final int promotionAppliedQuantity;
    private final int normalPriceQuantity;
    private final Promotion promotion;

    // 프로모션이 없는 경우
    public PromotionResult(int quantity) {
        this.status = PromotionStatus.NO_PROMOTION;
        this.promotionAppliedQuantity = 0;
        this.normalPriceQuantity = quantity;
        this.promotion = null;
    }

    // 수량 부족으로 프로모션 미적용
    public PromotionResult(Promotion promotion, int quantity) {
        this.status = PromotionStatus.INSUFFICIENT_QUANTITY;
        this.promotionAppliedQuantity = 0;
        this.normalPriceQuantity = quantity;
        this.promotion = promotion;
    }

    // 프로모션 정상 적용
    public PromotionResult(Promotion promotion, int promotionQuantity, int normalQuantity) {
        this.status = PromotionStatus.PROMOTION_APPLIED;
        this.promotionAppliedQuantity = promotionQuantity;
        this.normalPriceQuantity = normalQuantity;
        this.promotion = promotion;
    }

    // 부분 프로모션 적용
    public PromotionResult(Promotion promotion, int promotionQuantity, int normalQuantity, boolean partial) {
        this.status = PromotionStatus.PARTIAL_PROMOTION;
        this.promotionAppliedQuantity = promotionQuantity;
        this.normalPriceQuantity = normalQuantity;
        this.promotion = promotion;
    }

    public PromotionStatus getStatus() {
        return status;
    }

    public int getQuantity() {
        if (this.status == PromotionStatus.INSUFFICIENT_QUANTITY) {
            return this.normalPriceQuantity;
        }
        if (this.status == PromotionStatus.PROMOTION_APPLIED) {
            return this.promotionAppliedQuantity;
        }
        return this.promotionAppliedQuantity - this.normalPriceQuantity;
    }

    public int getPromotionQuantity() {
        return this.normalPriceQuantity - this.promotionAppliedQuantity;
    }
}
