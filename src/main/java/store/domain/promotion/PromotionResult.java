package store.domain.promotion;

import store.domain.promotion.type.PromotionStatus;

public class PromotionResult {
    private final PromotionStatus status;
    private final int promotionAppliedQuantity;
    private final int normalPriceQuantity;
    private final Promotion promotion;

    public PromotionResult(int quantity) {
        this.status = PromotionStatus.NO_PROMOTION;
        this.promotionAppliedQuantity = 0;
        this.normalPriceQuantity = quantity;
        this.promotion = null;
    }

    public PromotionResult(Promotion promotion, int quantity) {
        this.status = PromotionStatus.INSUFFICIENT_QUANTITY;
        this.promotionAppliedQuantity = 0;
        this.normalPriceQuantity = quantity;
        this.promotion = promotion;
    }

    public PromotionResult(Promotion promotion, int promotionQuantity, int normalQuantity) {
        this.status = PromotionStatus.PROMOTION_APPLIED;
        this.promotionAppliedQuantity = promotionQuantity;
        this.normalPriceQuantity = normalQuantity;
        this.promotion = promotion;
    }

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
        return Math.abs(this.normalPriceQuantity - this.promotionAppliedQuantity);
    }

    public int getPromotionQuantity() {
        return this.normalPriceQuantity - this.promotionAppliedQuantity;
    }

    public int getNormalPriceQuantity() {
        return this.normalPriceQuantity;
    }
}
