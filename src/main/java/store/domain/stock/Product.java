package store.domain.stock;

public record Product(String name,
                      int price,
                      int quantity,
                      String promotion) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Product other))
            return false;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + promotion.hashCode();
    }
}
