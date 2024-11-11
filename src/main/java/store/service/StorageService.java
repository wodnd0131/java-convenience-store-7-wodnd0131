package store.service;

import store.domain.promotion.Promotions;
import store.domain.stock.Stock;
import store.dto.StorageData;
import store.repository.FileRepository;

public class StorageService {
    private final FileRepository<Promotions> promotionRepository;
    private final FileRepository<Stock> stockRepository;

    public StorageService(
        FileRepository<Promotions> promotionRepository,
        FileRepository<Stock> stockRepository
    ) {
        this.promotionRepository = promotionRepository;
        this.stockRepository = stockRepository;
    }

    public StorageData load() {
        Stock stock = stockRepository.findAll();
        Promotions promotions = promotionRepository.findAll();
        return new StorageData(stock, promotions);
    }
}