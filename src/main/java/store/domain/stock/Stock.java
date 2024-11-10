package store.domain.stock;

import static store.common.constant.ErrorMessages.*;
import static store.view.OutputMessage.STOCK_INFO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import store.domain.shoppingList.ShoppingList;

public class Stock {
    private final Map<String, List<Product>> products;

    public Stock(LinkedHashMap<String, List<Product>> productMap) {
        this.products = productMap;
    }

    public void validateStockQuantity(ShoppingList shoppingList) {
        Set<String> productNames = shoppingList.getProductsNames();

        for (String productName : productNames) {
            int requestedQuantity = shoppingList.getQuantity(productName);
            int stockQuantity = getQuantity(productName);

            if (isExceedingStockQuantity(requestedQuantity, stockQuantity)) {
                throw new IllegalArgumentException(EXCEED_STOCK_QUANTITY.toString());
            }
        }
    }

    private boolean isExceedingStockQuantity(int requested, int inStock) {
        return requested > inStock;
    }

    private int getQuantity(String name) {
        try {
            return products.get(name).stream()
                .mapToInt(Product::quantity)
                .sum();
        } catch (NullPointerException e) {
            throw new NullPointerException(PRODUCT_NOT_FOUND.toString());
        }
    }

    @Override
    public String toString() {
        return formatStockInfo();
    }

    private String formatStockInfo() {
        return products.entrySet().stream()
            .flatMap(this::formatProductList)
            .collect(Collectors.joining("\n"));
    }

    private Stream<String> formatProductList(Map.Entry<String, List<Product>> entry) {
        return entry.getValue().stream()
            .map(this::formatProductInfo);
    }

    private String formatProductInfo(Product product) {
        return String.format(STOCK_INFO.getMessage(),
            product.name(),
            product.price(),
            product.quantity(),
            product.promotion());
    }
}
