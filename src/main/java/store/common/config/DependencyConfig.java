package store.common.config;

import store.controller.PurchaseController;
import store.controller.StoreController;
import store.domain.promotion.Promotions;
import store.domain.stock.Stock;
import store.repository.FileRepository;
import store.repository.PromotionsRepository;
import store.repository.StockRepository;
import store.controller.ResourceController;
import store.view.impl.ConsoleInputView;
import store.view.impl.ConsoleOutputView;
import store.view.interfaces.InputView;
import store.view.interfaces.OutputView;

public class DependencyConfig {

    public StoreController storeController() {
        return new StoreController(
            resourceController(),
            purchaseController()
        );
    }

    public PurchaseController purchaseController() {
        return new PurchaseController(
            inputView(),
            outputView()
        );
    }

    public InputView inputView() {
        return new ConsoleInputView();
    }

    public OutputView outputView() {
        return new ConsoleOutputView();
    }

    public ResourceController resourceController() {
        return new ResourceController(
            promotionRepository(),
            stockRepository()
        );
    }

    public FileRepository<Promotions> promotionRepository() {
        return new PromotionsRepository();
    }

    public FileRepository<Stock> stockRepository() {
        return new StockRepository();
    }
}