package store.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.domain.promotion.PromotionsFactory;
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

        Map<String, Promotion> expectedPromotion = new HashMap<>();
        expectedPromotion.put("탄산1+1", PromotionsFactory.createNonPromotion());
        Promotions expectedPromotions = new Promotions(expectedPromotion);

        stockRepository.setStock(expectedStock);
        promotionRepository.setPromotions(expectedPromotions);

        StorageData result = resourceController.load();

        assertNotNull(result);
        assertEquals(expectedStock, result.stock());
        assertEquals(expectedPromotions, result.promotions());
    }

    private static class TestPromotionRepository extends FileRepository<Promotions> {
        private Promotions promotions;

        protected TestPromotionRepository() {
            super("test.md");
        }

        public void setPromotions(Promotions promotions) {
            this.promotions = promotions;
        }

        @Override
        public Promotions findAll() {
            return promotions;
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
    }
}