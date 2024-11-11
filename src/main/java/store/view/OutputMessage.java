package store.view;

public enum OutputMessage {
    VISIT_INFORMATION_MESSAGE("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다."),
    REQUEST_PURCHASE("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    CHECK_MEMBERSHIP("멤버십 할인을 받으시겠습니까? (Y/N)"),
    CHECK_OTHER_PURCHASE("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),

    STOCK_INFO("- %s %,d원 %s개 %s"),
    STOCK_SOLD_OUT_INFO("- %s %,d원 재고 없음"),
    CHECK_NOT_APPLIED_PROMOTION("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n"),
    CHECK_BUY_N_GIVE_ONE("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n"),
    ;
    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}