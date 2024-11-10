package store.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.common.exception.ResourceReadException;
import store.domain.stock.Stock;

class StockRepositoryTest {
    static class TestStockRepository extends StockRepository {
        private final List<String> testLines;

        TestStockRepository(List<String> testLines) {
            this.testLines = testLines;
        }

        @Override
        protected List<String> readLines() {
            return testLines;
        }
    }

    @Test
    @DisplayName("유효한 재고 데이터를 읽어서 재고 객체로 반환한다")
    void findAll_WhenValidData_ReturnStock() {
        List<String> validType = List.of("Bu,si,ness,Rule",
            "콜라,1000,10,탄산2+1");
        StockRepository testRepository = new TestStockRepository(validType);

        Stock stock = testRepository.findAll();
        assertNotNull(stock);
        assertEquals("- 콜라 1,000원 10개 탄산2+1", stock.toString());
    }

    @Test
    @DisplayName("잘못된 형식의 재고 데이터를 읽으면 예외가 발생한다")
    void findAll_WhenInvalidData_ThrowsResourceReadException() {
        List<String> invalidLines = List.of("invalid,data,format");
        List<String> invalidType = List.of("invalid,data,format\nname,quantity,price,promotion");
        StockRepository testLinesRepository = new TestStockRepository(invalidLines);
        StockRepository testTypeRepository = new TestStockRepository(invalidType);

        assertThrows(ResourceReadException.class,
            testLinesRepository::findAll);
        assertThrows(ResourceReadException.class,
            testTypeRepository::findAll);
    }
}