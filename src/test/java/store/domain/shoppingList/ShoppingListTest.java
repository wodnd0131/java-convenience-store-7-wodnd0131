package store.domain.shoppingList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShoppingListTest {

    @Nested
    @DisplayName("생성자 테스트")
    class ConstructorTest {

        @Test
        @DisplayName("정상적인 입력값으로 ShoppingList 객체를 생성한다")
        void createShoppingList() {
            String input = "[apple-1][banana-2][orange-3]";
            ShoppingList shoppingList = new ShoppingList(input);

            assertThat(shoppingList.getProductsNames()).containsExactlyInAnyOrder("apple", "banana", "orange");
        }

        @Test
        @DisplayName("빈 문자열로 생성 시 예외가 발생한다")
        void createWithEmptyString() {
            String input = "";

            assertThrows(IllegalArgumentException.class,
                () -> new ShoppingList(input));
        }

        @Test
        @DisplayName("null 입력 시 예외가 발생한다")
        void createWithNull() {
            assertThrows(IllegalArgumentException.class,
                () -> new ShoppingList(null));
        }

        @Test
        @DisplayName("잘못된 형식의 입력 시 예외가 발생한다")
        void createWithInvalidFormat() {
            String input = "apple-1";

            assertThrows(IllegalArgumentException.class,
                () -> new ShoppingList(input));
        }
    }

    @Nested
    @DisplayName("상품 수량 검증 테스트")
    class QuantityValidationTest {

        @Test
        @DisplayName("상품의 수량이 0 이하일 경우 예외가 발생한다")
        void createWithZeroQuantity() {
            String input = "[apple-0]";

            assertThrows(IllegalArgumentException.class,
                () -> new ShoppingList(input));
        }

        @Test
        @DisplayName("상품의 수량이 음수일 경우 예외가 발생한다")
        void createWithNegativeQuantity() {
            String input = "[apple--1]";

            assertThrows(IllegalArgumentException.class,
                () -> new ShoppingList(input));
        }
    }

    @Nested
    @DisplayName("상품 조회 테스트")
    class ProductQueryTest {

        @Test
        @DisplayName("등록된 모든 상품의 이름을 조회한다")
        void getProductNames() {
            String input = "[apple-1][banana-2][orange-3]";
            ShoppingList shoppingList = new ShoppingList(input);

            Set<String> productNames = shoppingList.getProductsNames();

            assertThat(productNames).hasSize(3);
            assertThat(productNames).containsExactlyInAnyOrder("apple", "banana", "orange");
        }

        @Test
        @DisplayName("특정 상품의 수량을 조회한다")
        void getQuantity() {
            String input = "[apple-1][banana-2][orange-3]";
            ShoppingList shoppingList = new ShoppingList(input);

            assertThat(shoppingList.getQuantity("apple")).isEqualTo(1);
            assertThat(shoppingList.getQuantity("banana")).isEqualTo(2);
            assertThat(shoppingList.getQuantity("orange")).isEqualTo(3);
        }
    }
}