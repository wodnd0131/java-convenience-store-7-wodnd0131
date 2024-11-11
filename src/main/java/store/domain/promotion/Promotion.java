package store.domain.promotion;

public record Promotion(
    String name,
    int buy,
    int get,
    ActiveType type
) {
    public PromotionResult applyPromotion(int requestedQuantity, int availableStock) {
        if (name.isEmpty() || type == ActiveType.OFF) {
            return new PromotionResult(requestedQuantity);
        }

        if (canCompletePromotionSet(requestedQuantity, availableStock)) {
            return new PromotionResult(this, requestedQuantity);
        }

        return calculatePromotionResult(requestedQuantity, availableStock);
    }

    private boolean canCompletePromotionSet(int requestedQuantity, int availableStock) {
        int promotionSetSize = buy + get;
        int incompleteQuantity = requestedQuantity % promotionSetSize;
        int quantityToComplete = promotionSetSize - incompleteQuantity;

        boolean hasEnoughItemsForNewSet = incompleteQuantity >= buy;
        boolean hasEnoughStock = requestedQuantity + quantityToComplete <= availableStock;

        return hasEnoughItemsForNewSet && hasEnoughStock;
    }

    private PromotionResult calculatePromotionResult(int requestedQuantity, int availableStock) {
        int setSize = buy + get;
        int possibleSets = requestedQuantity / setSize;
        int promotionItems = possibleSets * setSize;
        int remainingItems = requestedQuantity % setSize;

        if (promotionItems > availableStock) {
            return createPartialPromotion(requestedQuantity, availableStock);
        }

        return new PromotionResult(this, promotionItems, remainingItems);
    }

    private PromotionResult createPartialPromotion(int requestedQuantity, int availableStock) {
        int setSize = buy + get;
        int possibleFullSets = availableStock / setSize;
        int promotionQuantity = possibleFullSets * setSize;
        int normalQuantity = requestedQuantity - promotionQuantity;

        return new PromotionResult(this, promotionQuantity, normalQuantity, true);
    }
}