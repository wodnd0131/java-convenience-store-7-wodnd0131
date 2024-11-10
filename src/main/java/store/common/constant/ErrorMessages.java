package store.common.constant;

public enum ErrorMessages {
    ERROR_PREFIX("[ERROR] ", Type.GENERAL),
    PARSING_INTEGER_ERROR("정수 변환 파싱 에러 입니다.", Type.GENERAL),
    PARSING_DATE_ERROR("날짜 변환 파싱 에러 입니다.", Type.GENERAL),
    LIST_LENGTH_ERROR("잘못된 데이터 값입니다. 필드 개수 오류입니다.", Type.GENERAL),
    NULL_OR_EMPTY_ERROR("잘못된 데이터 값입니다. 비어있는 값이 포함되었습니다.", Type.GENERAL),

    RESOURCE_FAILED_READ("저장소의 데이터를 읽을 수 없습니다 : ", Type.RESOURCE),
    RESOURCE_NOT_FOUND("저장소가 존재하지 않습니다 : ", Type.RESOURCE),

    INVALID_INPUT_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.", Type.INPUT),
    WRONG_INPUT("잘못된 입력입니다. 다시 입력해 주세요.", Type.INPUT),

    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요.", Type.PRODUCT),
    EXCEED_STOCK_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.", Type.PRODUCT),
    ;

    private final String message;
    private final Type type;

    ErrorMessages(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return ERROR_PREFIX.getMessage() + message;
    }

    private enum Type {
        GENERAL,
        INPUT,
        PRODUCT,
        RESOURCE
    }
}
