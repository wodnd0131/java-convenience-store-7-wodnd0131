package store.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionActive;
import store.domain.stock.Product;
import store.domain.stock.Stock;
import store.dto.StorageData;
import store.repository.FileRepository;

class ResourceControllerTest {
    private TestPromotionRepository promotionRepository;
    private TestStockRepository stockRepository;
    private ResourceController resourceController;

    @BeforeEach
    void setUp() {
        promotionRepository = new TestPromotionRepository();
        stockRepository = new TestStockRepository();
        resourceController = new ResourceController(promotionRepository, stockRepository);
    }

    @Test
    @DisplayName("load 메소드는 Stock과 Promotion 목록이 포함된 StorageData를 반환한다")
    void load_ShouldReturnStorageDataWithStockAndPromotions() {
        LinkedHashMap<String, List<Product>> productMap = new LinkedHashMap<>();
        productMap.put("name", List.of(new Product("name", 1, 1, "탄산1+1")));
        Stock expectedStock = new Stock(productMap);

        List<Promotion> expectedPromotions = Arrays.asList(
            new Promotion("탄산1+1", 2, 1, new PromotionActive(true))
        );

        stockRepository.setStock(expectedStock);
        promotionRepository.setPromotions(expectedPromotions);

        StorageData result = resourceController.load();

        assertNotNull(result);
        assertEquals(expectedStock, result.stock());
        assertEquals(expectedPromotions, result.promotions());
    }

    private static class TestPromotionRepository extends FileRepository<List<Promotion>> {
        private List<Promotion> promotions;

        protected TestPromotionRepository() {
            super("test.md");
        }

        public void setPromotions(List<Promotion> promotions) {
            this.promotions = promotions;
        }

        @Override
        public List<Promotion> findAll() {
            return promotions;
        }

        @Override
        public void save(List<Promotion> dto) {

        }
    }

    private static class TestStockRepository extends FileRepository<Stock> {
        private Stock stock;

        protected TestStockRepository() {
            super("test.md");
        }

        public void setStock(Stock stock) {
            this.stock = stock;
        }

        @Override
        public Stock findAll() {
            return stock;
        }

        @Override
        public void save(Stock dto) {

        }
    }
}