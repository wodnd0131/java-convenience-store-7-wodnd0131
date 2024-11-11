package store.domain.promotion;

public record Promotion(
    String name,
    int buy,
    int get,
    ActiveType type
) {
}