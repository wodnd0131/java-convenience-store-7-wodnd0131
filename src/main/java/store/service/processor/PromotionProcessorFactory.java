package store.service.processor;

import static store.domain.promotion.type.PromotionStatus.*;

import java.util.EnumMap;
import java.util.Map;

import store.domain.promotion.type.PromotionStatus;
import store.service.UserPromptService;

public class PromotionProcessorFactory {
    private final Map<PromotionStatus, PromotionProcessor> processors;

    public PromotionProcessorFactory(UserPromptService userPromptService) {
        processors = new EnumMap<>(PromotionStatus.class);
        processors.put(INSUFFICIENT_QUANTITY, new InsufficientQuantityProcessor(userPromptService));
        processors.put(PARTIAL_PROMOTION, new PartialPromotionProcessor(userPromptService));
        processors.put(NO_PROMOTION, new NormalPurchaseProcessor());
    }

    public PromotionProcessor getProcessor(PromotionStatus status) {
        return processors.getOrDefault(status, new NormalPurchaseProcessor());
    }
}

