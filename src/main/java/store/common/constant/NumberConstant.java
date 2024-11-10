package store.common.constant;

public enum NumberConstant {
    BUSINESS_RULE_INDEX(1),
    ;

    private final int value;

    NumberConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
