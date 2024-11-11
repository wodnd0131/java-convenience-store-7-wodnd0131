package store.domain.shoppingList;

import static store.domain.shoppingList.ReceiptConstant.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import store.domain.stock.Product;

public class Receipt {
    private final List<Product> products;
    private final Map<String, Integer> promotions;
    private boolean membership;

    public Receipt() {
        this(new ArrayList<>(), new HashMap<>(), false);
    }

    public Receipt(List<Product> products, Map<String, Integer> promotions, boolean membership) {
        this.products = products;
        this.promotions = promotions;
        this.membership = membership;
    }

    public void addProduct(Product product) {
        products.add(product);
        if (!product.promotion().isEmpty()) {
            promotions.replace(product.name(), promotions.getOrDefault(product.name(), product.quantity()));
        }
    }

    public void ActiveMembership() {
        this.membership = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendHeader(sb);
        appendProducts(sb);
        appendPromotions(sb);
        appendFooter(sb);
        return sb.toString();
    }

    private void appendHeader(StringBuilder sb) {
        sb.append(STORE_NAME.getString());
        sb.append(COLUMN_HEADER.getString());
    }

    private void appendProducts(StringBuilder sb) {
        for (Product product : products) {
            sb.append(String.format(
                ReceiptConstant.PRODUCT_FORMAT.getString(),
                product.name(),
                product.quantity(),
                product.price() * product.quantity()));
        }
    }

    private void appendPromotions(StringBuilder sb) {
        if (!promotions.isEmpty()) {
            sb.append(PROMOTION_HEADER.getString());
            for (Map.Entry<String, Integer> promotion : promotions.entrySet()) {
                sb.append(String.format(
                    PROMOTION_FORMAT.getString(),
                    promotion.getKey(),
                    promotion.getValue()));
            }
        }
    }

    private void appendFooter(StringBuilder sb) {
        sb.append(SEPARATOR.getString()).append("\n");

        int totalQuantity = calculateTotalQuantity();
        int totalAmount = calculateFinalAmount();

        appendPurchaseAmount(sb, totalQuantity, totalAmount);
        appendDiscounts(sb);
        appendFinalPayment(sb, totalAmount);
    }

    private int calculateFinalAmount() {
        int totalAmount = calculateTotalAmount();
        if (!promotions.isEmpty()) {
            totalAmount -= calculateEventDiscount();
        }
        if (membership) {
            totalAmount -= calculateMembershipDiscount();
        }
        return totalAmount;
    }

    private int calculateTotalQuantity() {
        return products.stream()
            .mapToInt(Product::quantity)
            .sum();
    }

    private int calculateTotalAmount() {
        return products.stream()
            .mapToInt(product -> product.price() * product.quantity())
            .sum();
    }

    private int calculateEventDiscount() {
        return promotions.entrySet().stream()
            .mapToInt(promotion -> products.stream()
                .filter(product -> product.name().equals(promotion.getKey()))
                .findFirst()
                .map(product -> product.price() * promotion.getValue())
                .orElse(0))
            .sum();
    }

    private int calculateMembershipDiscount() {
        int totalAmount = calculateTotalAmount();
        int eventDiscount = calculateEventDiscount();
        int amountAfterEventDiscount = totalAmount - eventDiscount;

        return (int)(amountAfterEventDiscount * MEMBERSHIP_DISCOUNT_RATE.getDouble());
    }

    private void appendPurchaseAmount(StringBuilder sb, int totalQuantity, int totalAmount) {
        sb.append(String.format(
            TOTAL_AMOUNT_FORMAT.getString(),
            totalQuantity,
            totalAmount));
    }

    private void appendDiscounts(StringBuilder sb) {
        if (!promotions.isEmpty()) {
            sb.append(String.format(
                EVENT_DISCOUNT_FORMAT.getString(), "",
                calculateEventDiscount()));
        }
        if (membership) {
            sb.append(String.format(
                MEMBERSHIP_DISCOUNT_FORMAT.getString(), "",
                calculateMembershipDiscount()));
        }
    }

    private void appendFinalPayment(StringBuilder sb, int totalAmount) {
        sb.append(String.format(
            FINAL_PAYMENT_FORMAT.getString(), "",
            totalAmount));
    }
}



