package store;

import store.common.config.DependencyConfig;
import store.controller.PurchaseController;

public class Application {
    public static void main(String[] args) {
        DependencyConfig config = new DependencyConfig();
        PurchaseController controller = config.purchaseController();
        controller.run();
    }
}
