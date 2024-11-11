package store.service.processor;

import store.domain.promotion.PromotionResult;
import store.domain.shoppingList.Receipt;
import store.domain.shoppingList.ShoppingList;
import store.domain.stock.Product;
import store.dto.StorageData;
import store.service.UserPromptService;

public class PartialPromotionProcessor implements PromotionProcessor {
    private final UserPromptService userPromptService;

    public PartialPromotionProcessor(UserPromptService userPromptService) {
        this.userPromptService = userPromptService;
    }

    @Override
    public void process(ShoppingList shoppingList, StorageData storageData,
        Receipt receipt, String productName, PromotionResult result) {
        int quantity = shoppingList.getQuantity(productName) - result.getQuantity();
        
        Product product = shoppingList.purchase(productName, storageData, quantity);
        receipt.addPromotions(product.name(), quantity);

        if (userPromptService.confirmNonPromotionalPurchase(productName, result.getQuantity())) {
            shoppingList.purchaseNonPromtion(productName, storageData, result.getQuantity());
            receipt.addProduct(new Product(product, quantity + result.getQuantity()));
        }
    }
}

