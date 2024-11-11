package store.service;

import static store.common.constant.ErrorMessages.*;
import static store.view.OutputMessage.*;

import store.view.interfaces.InputView;
import store.view.interfaces.OutputView;

public class UserPromptService {
    private static final String POSITIVE_RESPONSE = "Y";
    private static final String NEGATIVE_RESPONSE = "N";
    private final InputView inputView;
    private final OutputView outputView;

    public UserPromptService(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public boolean confirmAdditionalPurchase(String productName) {
        String message = String.format(CHECK_BUY_N_GIVE_ONE.getMessage(), productName);
        return promptUser(message);
    }

    public boolean confirmNonPromotionalPurchase(String productName, int quantity) {
        String message = String.format(CHECK_NOT_APPLIED_PROMOTION.getMessage(),
            productName, quantity);
        return promptUser(message);
    }

    public boolean confirmMembership() {
        return promptUser(CHECK_MEMBERSHIP.getMessage());
    }

    public boolean confirmContinueShopping() {
        return promptUser(CHECK_OTHER_PURCHASE.getMessage());
    }

    private boolean promptUser(String message) {
        outputView.println(message);
        String response = inputView.readLine().toUpperCase();
        validateResponse(response);
        return response.equals(POSITIVE_RESPONSE);
    }

    private void validateResponse(String response) {
        if (!response.equals(POSITIVE_RESPONSE) && !response.equals(NEGATIVE_RESPONSE)) {
            throw new IllegalArgumentException(WRONG_INPUT.toString());
        }
    }
}