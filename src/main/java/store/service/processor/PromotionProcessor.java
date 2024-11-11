package store.service.processor;

import store.domain.promotion.PromotionResult;
import store.domain.shoppingList.Receipt;
import store.domain.shoppingList.ShoppingList;
import store.dto.StorageData;

public interface PromotionProcessor {
    void process(ShoppingList shoppingList, StorageData storageData, Receipt receipt, String productName,
        PromotionResult result);
}
