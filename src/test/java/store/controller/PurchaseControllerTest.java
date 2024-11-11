package store.controller;

import static org.assertj.core.api.Assertions.*;
import static store.common.constant.ErrorMessages.*;
import static store.view.OutputMessage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.domain.shoppingList.ShoppingList;
import store.domain.stock.Stock;
import store.dto.StorageData;
import store.view.OutputMessage;
import store.view.interfaces.InputView;
import store.view.interfaces.OutputView;

class PurchaseControllerTest {

    private TestInputView inputView;
    private TestOutputView outputView;
    private TestStock stock;
    private TestPromotions protmotions;
    private PurchaseController purchaseController;
    private StorageData storageData;

    @BeforeEach
    void setUp() {
        inputView = new TestInputView();
        outputView = new TestOutputView();
        stock = new TestStock();
        protmotions = new TestPromotions();
        purchaseController = new PurchaseController(inputView, outputView);
        storageData = new StorageData(stock, protmotions);
    }

    @Nested
    @DisplayName("상품 구매 시")
    class Purchase {

        @Test
        @DisplayName("정상적인 입력값으로 상품을 구매한다")
        void purchaseWithValidInput() {
            inputView.addInput("[apple-1][banana-2]");

            purchaseController.purchase(storageData);

            assertThat(outputView.getMessages()).contains(
                VISIT_INFORMATION_MESSAGE.getMessage(),
                REQUEST_PURCHASE.getMessage()
            );
            assertThat(stock.getValidatedShoppingList()).isNotNull();
        }

        @Test
        @DisplayName("잘못된 입력 후 올바른 입력으로 구매를 진행한다")
        void purchaseWithInvalidThenValidInput() {
            inputView.addInput("invalid");
            inputView.addInput("[apple-1]");

            purchaseController.purchase(storageData);

            assertThat(outputView.getMessages()).contains(
                INVALID_INPUT_FORMAT.toString(),
                REQUEST_PURCHASE.getMessage()
            );
            assertThat(outputView.getMessageCount(REQUEST_PURCHASE.getMessage())).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("재고 검증 시")
    class StockValidation {

        @Test
        @DisplayName("재고보다 많은 수량 요청 시 재입력을 요구한다")
        void validateExceedingQuantity() {
            inputView.addInput("[apple-999]");
            inputView.addInput("[apple-1]");
            stock.setValidationExceptionCount(1);

            purchaseController.purchase(storageData);

            assertThat(outputView.getMessages()).contains(
                EXCEED_STOCK_QUANTITY.toString()
            );
            assertThat(outputView.getMessageCount(REQUEST_PURCHASE.getMessage())).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("입력값 검증 시")
    class InputValidation {

        @Test
        @DisplayName("빈 입력값 입력 시 재입력을 요구한다")
        void validateEmptyInput() {
            inputView.addInput("");
            inputView.addInput("[apple-1]");

            purchaseController.purchase(storageData);

            assertThat(outputView.getMessages()).contains(
                INVALID_INPUT_FORMAT.toString(),
                REQUEST_PURCHASE.getMessage()
            );
            assertThat(outputView.getMessageCount(REQUEST_PURCHASE.getMessage())).isEqualTo(2);
        }

        @Test
        @DisplayName("null 입력값 입력 시 재입력을 요구한다")
        void validateNullInput() {
            inputView.addInput(null);
            inputView.addInput("[apple-1]");

            purchaseController.purchase(storageData);

            assertThat(outputView.getMessages()).contains(
                INVALID_INPUT_FORMAT.toString(),
                REQUEST_PURCHASE.getMessage()
            );
            assertThat(outputView.getMessageCount(REQUEST_PURCHASE.getMessage())).isEqualTo(2);
        }

        @Test
        @DisplayName("잘못된 형식의 입력값 입력 시 재입력을 요구한다")
        void validateInvalidFormat() {
            inputView.addInput("apple-1");
            inputView.addInput("[apple-1]");

            purchaseController.purchase(storageData);

            assertThat(outputView.getMessages()).contains(
                INVALID_INPUT_FORMAT.toString(),
                REQUEST_PURCHASE.getMessage()
            );
            assertThat(outputView.getMessageCount(REQUEST_PURCHASE.getMessage())).isEqualTo(2);
        }
    }

    static class TestInputView implements InputView {
        private final Queue<String> inputs = new LinkedList<>();

        public void addInput(String input) {
            inputs.add(input);
        }

        @Override
        public String readLine() {
            return inputs.poll();
        }

        @Override
        public void close() throws Exception {

        }
    }

    static class TestOutputView implements OutputView {
        private final List<String> messages = new ArrayList<>();

        @Override
        public void println(String message) {
            messages.add(message);
        }

        @Override
        public void println(OutputMessage message, Object... args) {
        }

        public List<String> getMessages() {
            return messages;
        }

        public long getMessageCount(String message) {
            return messages.stream()
                .filter(m -> m.equals(message))
                .count();
        }
    }

    static class TestStock extends Stock {
        private ShoppingList validatedShoppingList;
        private int validationExceptionCount;
        private int validationAttempts;

        public TestStock() {
            super(null);
        }

        public void setValidationExceptionCount(int count) {
            this.validationExceptionCount = count;
            this.validationAttempts = 0;
        }

        @Override
        public void validateStockQuantity(ShoppingList shoppingList) {
            validationAttempts++;
            if (validationAttempts <= validationExceptionCount) {
                throw new IllegalArgumentException(EXCEED_STOCK_QUANTITY.toString());
            }
            this.validatedShoppingList = shoppingList;
        }

        public ShoppingList getValidatedShoppingList() {
            return validatedShoppingList;
        }

        @Override
        public String toString() {
            return "Test Stock";
        }
    }

    static class TestPromotions extends Promotions {
        public TestPromotions() {
            super(new HashMap<>());
        }

        @Override
        public String toString() {
            return "Test Promotions";
        }
    }
}