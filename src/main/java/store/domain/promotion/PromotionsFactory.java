package store.domain.promotion;

import static java.lang.Integer.parseInt;
import static store.common.constant.NumberConstant.BUSINESS_RULE_INDEX;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import store.common.util.DateTimesWrapper;
import store.domain.promotion.type.ActiveType;

public final class PromotionsFactory {
    private static final String SEPARATOR = ",";
    private static final int NAME_IDX = 0;
    private static final int BUY_IDX = 1;
    private static final int GET_IDX = 2;
    private static final int START_DATE_IDX = 3;
    private static final int END_DATE_IDX = 4;

    private PromotionsFactory() {
    }

    public static Promotions from(List<String> lines) {
        Map<String, Promotion> promotions = lines.stream()
            .skip(BUSINESS_RULE_INDEX.getValue())
            .map(line -> line.split(SEPARATOR))
            .map(PromotionsFactory::createPromotion)
            .collect(Collectors.toMap(
                Promotion::name,
                promotion -> promotion
            ));

        return new Promotions(promotions);
    }

    public static Promotion createNonPromotion() {
        return new Promotion("null", 1, 0, ActiveType.OFF);
    }

    private static Promotion createPromotion(String[] columns) {
        return createPromotionWith(columns, getActiveType(columns));
    }

    private static ActiveType getActiveType(String[] columns) {
        return DateRangeValidator.of(
            columns[START_DATE_IDX],
            columns[END_DATE_IDX],
            DateTimesWrapper.now()
        );
    }

    private static Promotion createPromotionWith(String[] columns, ActiveType type) {
        return new Promotion(
            columns[NAME_IDX],
            parseInt(columns[BUY_IDX]),
            parseInt(columns[GET_IDX]),
            type
        );
    }

}
