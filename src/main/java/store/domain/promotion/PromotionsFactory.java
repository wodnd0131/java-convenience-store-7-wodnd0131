package store.domain.promotion;

import static java.lang.Integer.parseInt;
import static store.common.constant.NumberConstant.BUSINESS_RULE_INDEX;

import java.util.List;

public final class PromotionsFactory {
    private static final String SEPARATOR = ",";
    private static final int NAME_IDX = 0;
    private static final int BUY_IDX = 1;
    private static final int GET_IDX = 2;
    private static final int START_DATE_IDX = 3;
    private static final int END_DATE_IDX = 4;

    private PromotionsFactory() {
    }

    public static List<Promotion> from(List<String> lines) {
        return lines.stream()
            .skip(BUSINESS_RULE_INDEX.getValue())
            .map(line -> line.split(SEPARATOR))
            .map(PromotionsFactory::createPromotion)
            .toList();
    }

    public static Promotion createPromotion(String[] columns) {
        return new Promotion(columns[NAME_IDX],
            parseInt(columns[BUY_IDX]),
            parseInt(columns[GET_IDX]),
            PromotionActiveFactory.of(columns[START_DATE_IDX], columns[END_DATE_IDX]));
    }
}
