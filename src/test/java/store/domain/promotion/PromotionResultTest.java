package store.domain.promotion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.domain.promotion.type.ActiveType;
import store.domain.promotion.type.PromotionStatus;

import static org.junit.jupiter.api.Assertions.*;

class PromotionResultTest {

    @Test
    @DisplayName("프로모션이 없는 경우 생성")
    void createWithNoPromotion() {
        PromotionResult result = new PromotionResult(5);

        assertEquals(PromotionStatus.NO_PROMOTION, result.getStatus());
        assertEquals(5, result.getNormalPriceQuantity());
        assertEquals(5, result.getQuantity());
    }

    @Test
    @DisplayName("수량 부족으로 프로모션 미적용")
    void createWithInsufficientQuantity() {
        Promotion promotion = new Promotion("테스트 프로모션", 2, 1, ActiveType.ON);
        PromotionResult result = new PromotionResult(promotion, 3);

        assertEquals(PromotionStatus.INSUFFICIENT_QUANTITY, result.getStatus());
        assertEquals(3, result.getPromotionQuantity());
        assertEquals(3, result.getNormalPriceQuantity());
        assertEquals(3, result.getQuantity());
    }

    @Test
    @DisplayName("프로모션 전체 적용")
    void createWithFullPromotion() {
        Promotion promotion = new Promotion("테스트 프로모션", 2, 1, ActiveType.ON);
        PromotionResult result = new PromotionResult(promotion, 2, 2);

        assertEquals(PromotionStatus.PROMOTION_APPLIED, result.getStatus());
        assertEquals(0, result.getPromotionQuantity());
        assertEquals(2, result.getNormalPriceQuantity());
        assertEquals(2, result.getQuantity());
    }

    @Test
    @DisplayName("프로모션 부분 적용")
    void createWithPartialPromotion() {
        Promotion promotion = new Promotion("테스트 프로모션", 2, 1, ActiveType.ON);
        PromotionResult result = new PromotionResult(promotion, 2, 5, true);

        assertEquals(PromotionStatus.PARTIAL_PROMOTION, result.getStatus());
        assertEquals(3, result.getPromotionQuantity());
        assertEquals(5, result.getNormalPriceQuantity());
        assertEquals(3, result.getQuantity());
    }

    @Test
    @DisplayName("수량 계산 - INSUFFICIENT_QUANTITY 상태")
    void getQuantityWithInsufficientQuantity() {
        Promotion promotion = new Promotion("테스트 프로모션", 2, 1, ActiveType.ON);
        PromotionResult result = new PromotionResult(promotion, 4);

        assertEquals(4, result.getQuantity());
    }

    @Test
    @DisplayName("수량 계산 - PROMOTION_APPLIED 상태")
    void getQuantityWithPromotionApplied() {
        Promotion promotion = new Promotion("테스트 프로모션", 2, 1, ActiveType.ON);
        PromotionResult result = new PromotionResult(promotion, 3, 6);

        assertEquals(3, result.getQuantity());
    }

    @Test
    @DisplayName("수량 계산 - PARTIAL_PROMOTION 상태")
    void getQuantityWithPartialPromotion() {
        Promotion promotion = new Promotion("테스트 프로모션", 2, 1, ActiveType.ON);
        PromotionResult result = new PromotionResult(promotion, 2, 5, true);

        assertEquals(3, result.getQuantity());
    }

    @Test
    @DisplayName("프로모션 수량 계산")
    void getPromotionQuantity() {
        Promotion promotion = new Promotion("테스트 프로모션", 2, 1, ActiveType.ON);
        PromotionResult result = new PromotionResult(promotion, 2, 5, true);

        assertEquals(3, result.getPromotionQuantity());
    }

    @Test
    @DisplayName("일반 가격 수량 확인")
    void getNormalPriceQuantity() {
        Promotion promotion = new Promotion("테스트 프로모션", 2, 1, ActiveType.ON);
        PromotionResult result = new PromotionResult(promotion, 2, 5, true);

        assertEquals(5, result.getNormalPriceQuantity());
    }
}