package store.controller;

import java.util.List;

import store.domain.promotion.Promotion;
import store.domain.stock.Stock;
import store.dto.StorageData;
import store.repository.FileRepository;

public class ResourceController {
    private final FileRepository<List<Promotion>> promotionRepository;
    private final FileRepository<Stock> stockRepository;

    public ResourceController(
        FileRepository<List<Promotion>> promotionRepository,
        FileRepository<Stock> stockRepository
    ) {
        this.promotionRepository = promotionRepository;
        this.stockRepository = stockRepository;
    }

    public StorageData load() {
        Stock stock = stockRepository.findAll();
        List<Promotion> promotions = promotionRepository.findAll();
        return new StorageData(stock, promotions);
    }

    public void save(StorageData storageData) {
    }

}
