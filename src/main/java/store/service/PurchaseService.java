package store.service;

import java.util.Set;

import store.domain.promotion.PromotionResult;
import store.domain.shoppingList.Receipt;
import store.domain.shoppingList.ShoppingList;
import store.dto.StorageData;
import store.service.processor.PromotionProcessor;
import store.service.processor.PromotionProcessorFactory;

public class PurchaseService {
    private final PromotionProcessorFactory promotionProcessorFactory;

    public PurchaseService(PromotionProcessorFactory promotionProcessorFactory) {
        this.promotionProcessorFactory = promotionProcessorFactory;
    }

    public Receipt processPromotion(ShoppingList shoppingList, StorageData storageData) {
        Receipt receipt = new Receipt();
        Set<String> productNames = shoppingList.getProductsNames();

        for (String productName : productNames) {
            PromotionResult result = shoppingList.isPromotion(productName, storageData);
            PromotionProcessor processor = promotionProcessorFactory.getProcessor(result.getStatus());
            processor.process(shoppingList, storageData, receipt, productName, result);
        }

        return receipt;
    }
}