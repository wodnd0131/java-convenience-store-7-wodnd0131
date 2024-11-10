package store.controller;

import static store.view.OutputMessage.*;

import java.util.function.Supplier;

import store.domain.shoppingList.ShoppingList;
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
        return handleReEnter(() -> {
            outputView.println(VISIT_INFORMATION_MESSAGE.getMessage());
            outputView.println(storageData.stock().toString());
            outputView.println(REQUEST_PURCHASE.getMessage());
            return new ShoppingList(inputView.readLine());
        });
    }

    private <T> T handleReEnter(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (IllegalArgumentException e) {
            outputView.println(e.getMessage());
            return handleReEnter(inputSupplier);
        }
    }
}
