package store.controller;

import static store.view.OutputMessage.*;

import java.util.function.Supplier;

import store.domain.shoppingList.Receipt;
import store.domain.shoppingList.ShoppingList;
import store.dto.StorageData;
import store.service.PurchaseService;
import store.service.StorageService;
import store.service.UserPromptService;
import store.view.interfaces.InputView;
import store.view.interfaces.OutputView;

public class PurchaseController {
    private final InputView inputView;
    private final OutputView outputView;
    private final PurchaseService purchaseService;
    private final UserPromptService userPromptService;
    private final StorageService storageService;

    public PurchaseController(
        InputView inputView,
        OutputView outputView,
        PurchaseService purchaseService,
        UserPromptService userPromptService, StorageService storageService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.purchaseService = purchaseService;
        this.userPromptService = userPromptService;
        this.storageService = storageService;
    }

    public void run() {
        StorageData storageData = storageService.load();
        do {
            ShoppingList shoppingList = receiveShoppingList(storageData);
            Receipt receipt = purchaseService.processPromotion(shoppingList, storageData);
            if (userPromptService.confirmMembership()) {
                receipt.ActiveMembership(true);
            }

            printReceipt(receipt);
        } while (userPromptService.confirmContinueShopping());
    }

    private ShoppingList receiveShoppingList(StorageData storageData) {
        outputView.println(VISIT_INFORMATION_MESSAGE.getMessage());
        outputView.println(storageData.stock().toString());

        return handleReEnter(() -> {
            outputView.println(REQUEST_PURCHASE.getMessage());
            String input = inputView.readLine();
            ShoppingList shoppingList = new ShoppingList(input);
            storageData.stock().validateStockQuantity(shoppingList);
            return shoppingList;
        });
    }

    private void printReceipt(Receipt receipt) {
        outputView.println(receipt.toString());
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
