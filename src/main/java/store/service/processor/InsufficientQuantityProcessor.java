package store.service.processor;

import store.domain.promotion.PromotionResult;
import store.domain.shoppingList.Receipt;
import store.domain.shoppingList.ShoppingList;
import store.domain.stock.Product;
import store.dto.StorageData;
import store.service.UserPromptService;

public class InsufficientQuantityProcessor implements PromotionProcessor {
    private final UserPromptService userPromptService;

    public InsufficientQuantityProcessor(UserPromptService userPromptService) {
        this.userPromptService = userPromptService;
    }

    @Override
    public void process(ShoppingList shoppingList, StorageData storageData,
        Receipt receipt, String productName, PromotionResult result) {
        int completedQuantity = result.getQuantity();
        if (userPromptService.confirmAdditionalPurchase(productName)) {
            completedQuantity++;
        }

        Product product = shoppingList.purchase(productName, storageData, completedQuantity);
        receipt.addProduct(new Product(product, completedQuantity));
        receipt.addPromotions(product.name(), completedQuantity - result.getPromotionQuantity());
    }
}

