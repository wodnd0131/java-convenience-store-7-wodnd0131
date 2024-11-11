package store.domain.stock;

import static java.lang.Integer.*;
import static store.common.constant.NumberConstant.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class StockFactory {
    private static final String SEPARATOR = ",";
    private static final int NAME_IDX = 0;
    private static final int PRICE_IDX = 1;
    private static final int QUANTITY_IDX = 2;
    private static final int PROMOTION_IDX = 3;

    private StockFactory() {
    }

    public static Stock from(List<String> lines) {
        return new Stock(createProductMap(lines));
    }

    public static LinkedHashMap<String, List<Product>> createProductMap(List<String> lines) {
        List<String[]> splitLines = getSplitLines(lines);
        return groupProductsByName(splitLines);
    }

    private static List<String[]> getSplitLines(List<String> lines) {
        return lines.stream()
            .skip(BUSINESS_RULE_INDEX.getValue())
            .map(line -> line.split(SEPARATOR))
            .collect(Collectors.toList());
    }

    private static LinkedHashMap<String, List<Product>> groupProductsByName(List<String[]> productLines) {
        LinkedHashMap<String, List<Product>> groupedProducts = productLines.stream()
            .collect(Collectors.groupingBy(
                StockFactory::getNamePart,
                LinkedHashMap::new,
                Collectors.mapping(
                    StockFactory::createProduct,
                    Collectors.toList()
                )
            ));
        return duplicatePromotionalProducts(groupedProducts);
    }

    private static LinkedHashMap<String, List<Product>> duplicatePromotionalProducts(
        LinkedHashMap<String, List<Product>> groupedProducts) {

        groupedProducts.forEach((name, products) -> {
            if (isSinglePromotionalProduct(products)) {
                Product originalProduct = products.get(0);
                Product nonPromotionalCopy = createNonPromotionalCopy(originalProduct);
                products.add(nonPromotionalCopy);
            }
        });

        return groupedProducts;
    }

    private static boolean isSinglePromotionalProduct(List<Product> products) {
        return products.size() == 1 &&
            hasPromotion(products.get(0));
    }

    private static boolean hasPromotion(Product product) {
        return product.promotion() != null && !product.promotion().isEmpty();
    }

    private static Product createNonPromotionalCopy(Product original) {
        return new Product(
            original.name(),
            original.price(),
            0,
            ""
        );
    }

    private static String getNamePart(String[] parts) {
        return parts[NAME_IDX];
    }

    private static Product createProduct(String[] parts) {
        String promotion = parts[PROMOTION_IDX];
        if (promotion.equals("null")) {
            promotion = "";
        }
        return new Product(
            parts[NAME_IDX],
            parseInt(parts[PRICE_IDX]),
            parseInt(parts[QUANTITY_IDX]),
            promotion
        );
    }

}
