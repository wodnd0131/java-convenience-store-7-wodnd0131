package store.controller;

import static store.view.OutputMessage.*;

import java.util.function.Supplier;

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
        ShoppingList shoppingList = addProdectOnCart(storageData);
    }

    private ShoppingList addProdectOnCart(StorageData storageData) {
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
