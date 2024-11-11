package store.controller;

import static store.common.constant.ErrorMessages.*;
import static store.domain.promotion.PromotionStatus.*;
import static store.view.OutputMessage.*;

import java.util.Set;
import java.util.function.Supplier;

import store.domain.promotion.PromotionResult;
import store.domain.shoppingList.Receipt;
import store.domain.shoppingList.ShoppingList;
import store.domain.stock.Product;
import store.domain.stock.Stock;
import store.dto.StorageData;
import store.view.interfaces.InputView;
import store.view.interfaces.OutputView;

public class PurchaseController {
    private static final String POSITIVE_RESPONSE = "Y";
    private static final String NEGATIVE_RESPONSE = "N";
    private final InputView inputView;
    private final OutputView outputView;

    public PurchaseController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void purchase(StorageData storageData) {
        do {
            ShoppingList shoppingList = addProductOnCart(storageData);
            Receipt receipt = checkPromotion(shoppingList, storageData);
            receipt.ActiveMembership(wantsToContinue(CHECK_MEMBERSHIP.getMessage()));
            printReceipt(receipt);
        } while (wantsToContinue(CHECK_OTHER_PURCHASE.getMessage()));
    }

    private boolean wantsToContinue(String message) {
        return handleReEnter(() -> {
            outputView.println(message);
            String response = inputView.readLine().toUpperCase();
            validateResponse(response);
            return response.equals(POSITIVE_RESPONSE);
        });
    }

    private void validateResponse(String response) {
        if (!response.equals(POSITIVE_RESPONSE) && !response.equals(NEGATIVE_RESPONSE)) {
            throw new IllegalArgumentException(WRONG_INPUT.toString());
        }
    }

    private void printReceipt(Receipt receipt) {
        outputView.println(receipt.toString());
    }

    private Receipt checkPromotion(ShoppingList shoppingList, StorageData storageData) {
        Receipt receipt = new Receipt();
        Set<String> productNames = shoppingList.getProductsNames();

        for (String productName : productNames) {
            PromotionResult result = shoppingList.isPromotion(productName, storageData);
            handlePromotionResult(shoppingList, storageData, receipt, productName, result);
        }

        return receipt;
    }

    private void handlePromotionResult(ShoppingList shoppingList, StorageData storageData,
        Receipt receipt, String productName, PromotionResult result) {
        if (result.getStatus() == INSUFFICIENT_QUANTITY) {
            handleInsufficientQuantity(shoppingList, storageData, receipt, productName, result);
            return;
        }
        if (result.getStatus() == PARTIAL_PROMOTION) {
            handlePartialPromotion(shoppingList, storageData, receipt, productName, result);
            return;
        }
        Product product = shoppingList.purchase(productName, storageData, result.getQuantity());
        receipt.addProduct(new Product(product, result.getQuantity()));
    }

    private void handleInsufficientQuantity(ShoppingList shoppingList, StorageData storageData,
        Receipt receipt, String productName, PromotionResult result) {
        String message = String.format(CHECK_BUY_N_GIVE_ONE.getMessage(), productName);
        int completedQuantity = result.getQuantity();
        if (wantsToContinue(message)) {
            completedQuantity++;
        }
        Product product = shoppingList.purchase(productName, storageData, completedQuantity);
        receipt.addProduct(new Product(product, completedQuantity));
        receipt.addPromotions(product.name(), completedQuantity - result.getPromotionQuantity());
    }

    private void handlePartialPromotion(ShoppingList shoppingList, StorageData storageData,
        Receipt receipt, String productName, PromotionResult result) {
        String message = String.format(CHECK_NOT_APPLIED_PROMOTION.getMessage(),
            productName, result.getQuantity());
        int quantity = shoppingList.getQuantity(productName) - result.getQuantity();
        Product product = shoppingList.purchase(productName, storageData, quantity);
        receipt.addPromotions(product.name(), quantity);
        if (wantsToContinue(message)) {
            shoppingList.purchaseNonPromtion(productName, storageData, result.getQuantity());
            receipt.addProduct(new Product(product, quantity + result.getQuantity()));
        }
    }

    private ShoppingList addProductOnCart(StorageData storageData) {
        outputView.println(VISIT_INFORMATION_MESSAGE.getMessage());
        outputView.println(storageData.stock().toString());
        return handleReEnter(() -> {
            outputView.println(REQUEST_PURCHASE.getMessage());
            String input = inputView.readLine();
            return createShoppingList(input, storageData.stock());
        });
    }

    private ShoppingList createShoppingList(String input, Stock stock) {
        ShoppingList shoppingList = new ShoppingList(input);
        stock.validateStockQuantity(shoppingList);
        return shoppingList;
    }

    private <T> T handleReEnter(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (IllegalArgumentException | NullPointerException e) {
            outputView.println(e.getMessage());
            return handleReEnter(inputSupplier);
        }
    }
}
