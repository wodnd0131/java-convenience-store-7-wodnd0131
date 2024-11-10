package store.domain.stock;

import java.util.Map;
import java.util.stream.Collectors;

public class Stock {
    private Map<Product, Integer> products;

    public Stock(Map<Product, Integer> productMap) {
        this.products = productMap;
    }

    @Override
    public String toString() {
        return products.entrySet().stream()
            .map(e -> String.format("- %s %,d원 %d개 %s",
                e.getKey().name(),
                e.getKey().price(),
                e.getValue(),
                e.getKey().promotion()))
            .collect(Collectors.joining("\n"));
    }
}
