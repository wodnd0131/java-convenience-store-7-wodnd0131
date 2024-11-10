package store.repository;

import java.util.List;

import store.common.exception.ResourceReadException;
import store.domain.stock.Stock;
import store.domain.stock.StockFactory;

public class StockRepository extends FileRepository<Stock> {
    private static final String RESOURCE_STOCK_PATH = "/products.md";

    public StockRepository() {
        super(RESOURCE_STOCK_PATH);
    }

    @Override
    public Stock findAll() {
        try {
            List<String> lines = readLines();
            validateNotEmpty(lines);
            return StockFactory.from(lines);
        } catch (RuntimeException e) {
            throw new ResourceReadException(e.getMessage());
        }
    }

    @Override
    public void save(Stock dto) {

    }
}

