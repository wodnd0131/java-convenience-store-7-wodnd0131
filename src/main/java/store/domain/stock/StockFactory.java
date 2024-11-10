package store.domain.stock;

import static java.lang.Integer.*;
import static java.util.stream.Collectors.*;
import static store.common.constant.ErrorMessages.*;
import static store.common.constant.NumberConstant.*;

import java.util.List;
import java.util.Map;

public final class StockFactory {
    private static final String SEPARATOR = ",";
    private static final int NAME_IDX = 0;
    private static final int PRICE_IDX = 1;
    private static final int QUANTITY_IDX = 2;
    private static final int PROMOTION_IDX = 3;

    private StockFactory() {
    }

    public static Stock from(List<String> lines) {
        validateNotEmpty(lines);
        Map<Product, Integer> productMap = lines.stream()
            .skip(BUSINESS_RULE_INDEX.getValue())
            .map(line -> line.split(SEPARATOR))
            .collect(toMap(
                StockFactory::createProduct,
                StockFactory::parseQuantity
            ));
        return new Stock(productMap);
    }

    private static void validateNotEmpty(List<String> lines) {
        if (isEmptyLines(lines)) {
            throw new IllegalArgumentException(NULL_OR_EMPTY_ERROR.getMessage());
        }
    }

    private static boolean isEmptyLines(List<String> lines) {
        return lines == null
            || lines.isEmpty()
            || hasInsufficientLines(lines);
    }

    private static boolean hasInsufficientLines(List<String> lines) {
        return lines.size() < BUSINESS_RULE_INDEX.getValue();
    }

    private static Product createProduct(String[] parts) {
        return new Product(
            parts[NAME_IDX],
            parseInt(parts[PRICE_IDX]),
            parts[PROMOTION_IDX]
        );
    }

    private static int parseQuantity(String[] parts) {
        String quantity = parts[QUANTITY_IDX];
        return parseInt(quantity);
    }
}
