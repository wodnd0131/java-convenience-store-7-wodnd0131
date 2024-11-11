package store.controller;

import static store.view.OutputMessage.*;

import java.util.Set;
import java.util.function.Supplier;

import store.domain.shoppingList.Receipt;
import store.domain.shoppingList.ShoppingList;
import store.domain.stock.Stock;
import store.dto.StorageData;
import store.view.interfaces.InputView;
import store.view.interfaces.OutputView;

public class PurchaseController {
    private final InputView inputView;
    private final OutputView outputView;

    public PurchaseController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void purchase(StorageData storageData) {
        ShoppingList shoppingList = addProductOnCart(storageData);
        applyPromotion(shoppingList, storageData);
    }

    private void applyPromotion(ShoppingList shoppingList, StorageData storageData) {
        Set<String> productNames = shoppingList.getProductsNames();
        Receipt receipt = new Receipt();
        for (String productName : productNames) {
            if (!shoppingList.isPromotion(productName, storageData)) {
                shoppingList.purchase(productName, storageData, receipt);
            }

        }
        System.out.println(storageData.stock());
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
