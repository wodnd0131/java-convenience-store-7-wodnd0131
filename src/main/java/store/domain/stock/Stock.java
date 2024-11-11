package store.domain.stock;

import static store.common.constant.ErrorMessages.*;
import static store.view.OutputMessage.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        String format = getFormatByQuantity(product.quantity());
        return String.format(format,
            product.name(),
            product.price(),
            product.quantity(),
            product.promotion()
        );
    }

    private String getFormatByQuantity(int quantity) {
        if (quantity == 0) {
            return STOCK_SOLD_OUT_INFO.getMessage();
        }
        return STOCK_INFO.getMessage();
    }

    public Product findProduct(String productName) {
        List<Product> productList = products.get(productName);
        if (productList == null || productList.isEmpty()) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND.toString());
        }
        return productList.getFirst();
    }

    public Product findNonPromtionProduct(String productName) {
        List<Product> productList = products.get(productName);
        if (productList == null || productList.isEmpty()) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND.toString());
        }
        return productList.getLast();
    }
}
