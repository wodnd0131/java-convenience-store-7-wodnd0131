package store.repository;

import static store.common.constant.NumberConstant.BUSINESS_RULE_INDEX;

import java.util.List;

import store.common.exception.ResourceReadException;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.domain.promotion.PromotionsFactory;

public class PromotionsRepository extends FileRepository<Promotions> {
    private static final String RESOURCE_PROMOTION_PATH = "/promotions.md";

    public PromotionsRepository() {
        super(RESOURCE_PROMOTION_PATH);
    }

    @Override
    public Promotions findAll() {
        try {
            List<String> lines = readLines();
            validateNotEmpty(lines);
            return PromotionsFactory.from(lines);
        } catch (RuntimeException e) {
            throw new ResourceReadException(e.getMessage());
        }
    }

    @Override
    public void save(Promotions dto) {

    }
}
