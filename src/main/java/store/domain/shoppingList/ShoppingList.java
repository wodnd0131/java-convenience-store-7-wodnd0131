package store.domain.shoppingList;

import static store.common.constant.ErrorMessages.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionResult;
import store.domain.stock.Product;
import store.dto.StorageData;

public class ShoppingList {
    private static final String REGEX_ITEM = "\\[(.*?)-(-?\\d+)\\]";
    private static final Pattern PATTERN_ITEM = Pattern.compile(REGEX_ITEM);

    private final Map<String, Integer> products;

    public ShoppingList(String input) {
        validateInput(input);
        this.products = parseProducts(input);
    }

    private void validateInput(String input) {
        if (isEmptyInput(input)) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT.toString());
        }
    }

    private boolean isEmptyInput(String input) {
        return input == null || input.trim().isEmpty();
    }

    private Map<String, Integer> parseProducts(String input) {
        Matcher matcher = PATTERN_ITEM.matcher(input);
        validateShoppingListFormat(matcher);
        return createProductQuantityMap(matcher);
    }

    private void validateShoppingListFormat(Matcher matcher) {
        if (!matcher.find()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT.toString());
        }
    }

    private Map<String, Integer> createProductQuantityMap(Matcher matcher) {
        Map<String, Integer> parsedProducts = new HashMap<>();
        do {
            String productName = matcher.group(1).trim();
            int quantity = Integer.parseInt(matcher.group(2));

            validateProduct(productName, quantity);
            parsedProducts.put(productName, quantity);
        } while (matcher.find());

        return parsedProducts;
    }

    private void validateProduct(String productName, int quantity) {
        validateProductName(productName);
        validateQuantity(productName, quantity);
    }

    private void validateProductName(String productName) {
        if (productName.isEmpty()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT.toString());
        }
    }

    private void validateQuantity(String productName, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(String.format(WRONG_INPUT.toString(), productName));
        }
    }

    public Set<String> getProductsNames() {
        return products.keySet();
    }

    public Integer getQuantity(String name) {
        return products.get(name);
    }

    public PromotionResult isPromotion(String productName, StorageData storageData) {
        Product product = storageData.stock().findProduct(productName);
        Promotion promotion = storageData.promotions().findPromotion(product.promotion());
        int requestedQuantity = getQuantity(productName);
        return product.checkPromotion(requestedQuantity, promotion);
    }

    public Product purchase(String productName, StorageData storageData, int quantity) {
        Product product = storageData.stock().findProduct(productName);
        product.purchase(quantity);
        return product;
    }

    public Product purchaseNonPromtion(String productName, StorageData storageData, int quantity) {
        Product product = storageData.stock().findNonPromtionProduct(productName);
        product.purchase(quantity);
        return product;
    }
}
