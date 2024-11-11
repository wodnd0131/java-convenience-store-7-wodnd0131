package store.domain.shoppingList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import store.domain.stock.Product;

class ReceiptTest {

    @Test
    @DisplayName("기본 영수증 생성")
    void createDefaultReceipt() {
        Receipt receipt = new Receipt();
        String result = receipt.toString();

        assertTrue(result.contains("총구매액\t\t0"));
    }

    @Test
    @DisplayName("상품 추가")
    void addProduct() {
        Receipt receipt = new Receipt();
        Product product = new TestProduct("테스트상품", 1000, 2);

        receipt.addProduct(product);
        String result = receipt.toString();

        assertTrue(result.contains("테스트상품"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("2,000"));
    }

    @Test
    @DisplayName("프로모션 추가")
    void addPromotions() {
        Receipt receipt = new Receipt();
        Product product = new TestProduct("테스트상품", 1000, 2);
        receipt.addProduct(product);

        receipt.addPromotions("테스트상품", 1);
        String result = receipt.toString();

        assertTrue(result.contains("====================================\n"
            + "총구매액\t\t2\t\t       2,000"));
        assertTrue(result.contains("=============증\t정===============\n"
            + "테스트상품   \t1"));
    }

    @Test
    @DisplayName("프로모션 수량이 0일 때 추가하지 않음")
    void addPromotionsWithZeroQuantity() {
        Receipt receipt = new Receipt();
        receipt.addPromotions("테스트상품", 0);
        String result = receipt.toString();

        assertFalse(result.contains("행사 할인"));
    }

    @Test
    @DisplayName("멤버십 할인 적용")
    void activeMembership() {
        Receipt receipt = new Receipt();
        Product product = new TestProduct("테스트상품", 10000, 1);
        receipt.addProduct(product);

        receipt.ActiveMembership(true);
        String result = receipt.toString();

        assertTrue(result.contains("멤버십할인                         3,000"));
        assertTrue(result.contains("내실돈                    7,000"));
    }

    @Test
    @DisplayName("복합 할인 적용")
    void multipleDiscounts() {
        Receipt receipt = new Receipt();
        Product product = new TestProduct("테스트상품", 10000, 2);
        receipt.addProduct(product);

        receipt.addPromotions("테스트상품", 1);
        receipt.ActiveMembership(true);
        String result = receipt.toString();

        assertTrue(result.contains("행사할인"));
        assertTrue(result.contains("멤버십할인"));
        assertTrue(result.contains("총구매액\t\t2\t\t      20,000"));
        assertTrue(result.contains("내실돈                    7,000"));
    }

    @Test
    @DisplayName("여러 상품 추가")
    void addMultipleProducts() {
        Receipt receipt = new Receipt();
        receipt.addProduct(new TestProduct("상품1", 1000, 2));
        receipt.addProduct(new TestProduct("상품2", 2000, 1));

        String result = receipt.toString();

        assertTrue(result.contains("상품1"));
        assertTrue(result.contains("상품2"));
        assertTrue(result.contains("총구매액\t\t3"));
        assertTrue(result.contains("내실돈                    4,000"));
    }

    private static class TestProduct extends Product {
        private final String name;
        private final int price;
        private final int quantity;

        TestProduct(String name, int price, int quantity) {
            super(name, price, quantity, null);
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public int price() {
            return price;
        }

        @Override
        public int quantity() {
            return quantity;
        }
    }
}