package store.common.config;

import store.controller.PurchaseController;
import store.domain.promotion.Promotions;
import store.domain.stock.Stock;
import store.repository.FileRepository;
import store.repository.PromotionsRepository;
import store.repository.StockRepository;
import store.service.PurchaseService;
import store.service.StorageService;
import store.service.UserPromptService;
import store.service.processor.PromotionProcessorFactory;
import store.view.impl.ConsoleInputView;
import store.view.impl.ConsoleOutputView;
import store.view.interfaces.InputView;
import store.view.interfaces.OutputView;

public final class DependencyConfig {

    public PurchaseController purchaseController() {
        return new PurchaseController(
            inputView(),
            outputView(),
            purchaseService(),
            userPromptService(),
            storageService()
        );
    }

    private InputView inputView() {
        return new ConsoleInputView();
    }

    private OutputView outputView() {
        return new ConsoleOutputView();
    }

    private UserPromptService userPromptService() {
        return new UserPromptService(inputView(), outputView());
    }

    private PurchaseService purchaseService() {
        return new PurchaseService(promotionProcessorFactory());
    }

    private PromotionProcessorFactory promotionProcessorFactory() {
        return new PromotionProcessorFactory(userPromptService());
    }

    private StorageService storageService() {
        return new StorageService(
            promotionRepository(),
            stockRepository()
        );
    }

    private FileRepository<Promotions> promotionRepository() {
        return new PromotionsRepository();
    }

    private FileRepository<Stock> stockRepository() {
        return new StockRepository();
    }
}