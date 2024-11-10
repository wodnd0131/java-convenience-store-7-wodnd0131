package store.dto;

import java.util.List;

import store.domain.promotion.Promotion;
import store.domain.stock.Stock;

public record StorageData(Stock stock, List<Promotion> promotions) {
}
