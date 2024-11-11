package store.dto;

import store.domain.promotion.Promotions;
import store.domain.stock.Stock;

public record StorageData(Stock stock, Promotions promotions) {
}
