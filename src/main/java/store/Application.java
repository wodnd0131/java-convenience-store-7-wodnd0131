package store;

import store.common.config.DependencyConfig;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        DependencyConfig config = new DependencyConfig();
        StoreController controller = config.storeController();
        controller.run();
    }
}
