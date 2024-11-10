package store.domain.stock;

import static store.view.OutputMessage.STOCK_INFO;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Stock {
    private final Map<Product, Integer> products;

    public Stock(LinkedHashMap<Product, Integer> productMap) {
        this.products = productMap;
    }

    public Integer getQuantity(Product product) {
        return products.get(product);
    }

    @Override
    public String toString() {
        return products.entrySet().stream()
            .map(e -> String.format(STOCK_INFO.getMessage(),
                e.getKey().name(),
                e.getKey().price(),
                e.getValue(),
                e.getKey().promotion()))
            .collect(Collectors.joining("\n"));
    }
}
