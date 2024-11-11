package store.service.processor;

import store.domain.promotion.PromotionResult;
import store.domain.shoppingList.Receipt;
import store.domain.shoppingList.ShoppingList;
import store.domain.stock.Product;
import store.dto.StorageData;
import store.service.UserPromptService;

public class NormalPurchaseProcessor implements PromotionProcessor {
    @Override
    public void process(ShoppingList shoppingList, StorageData storageData,
        Receipt receipt, String productName, PromotionResult result) {
        Product product = shoppingList.purchase(productName, storageData, result.getQuantity());
        receipt.addProduct(new Product(product, result.getQuantity()));
    }
}

