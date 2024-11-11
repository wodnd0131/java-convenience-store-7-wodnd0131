package store.domain.shoppingList;

public enum ReceiptConstant {
    MEMBERSHIP_DISCOUNT_RATE(0.3),
    STORE_NAME("==============W 편의점================\n"),
    SEPARATOR("===================================="),
    PROMOTION_HEADER("=============증\t정===============\n"),
    COLUMN_HEADER("상품명\t\t수량\t\t금액\n"),

    PRODUCT_FORMAT("%-8s\t%d \t\t%,d\n"),
    PROMOTION_FORMAT("%-8s\t%d\n"),
    TOTAL_AMOUNT_FORMAT("총구매액\t\t%d\t\t%,12d\n"),
    EVENT_DISCOUNT_FORMAT("행사할인%20s%,d\n"),
    MEMBERSHIP_DISCOUNT_FORMAT("멤버십할인%25s%,d\n"),
    FINAL_PAYMENT_FORMAT("내실돈%20s%,d\n");

    private final String stringValue;
    private final double doubleValue;

    ReceiptConstant(String value) {
        this.stringValue = value;
        this.doubleValue = 0.0;
    }

    ReceiptConstant(double value) {
        this.stringValue = "";
        this.doubleValue = value;
    }

    public String getString() {
        return stringValue;
    }

    public double getDouble() {
        return doubleValue;
    }
}
