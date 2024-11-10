package store.controller;

import store.dto.StorageData;

public class StoreController {
    private final ResourceController resourceController;
    private final PurchaseController purchaseController;

    public StoreController(ResourceController resourceController,
        PurchaseController purchaseController) {

        this.resourceController = resourceController;
        this.purchaseController = purchaseController;
    }

    public void run() {
        StorageData storageData = resourceController.load();
        purchaseController.purchase(storageData);
    }
}
