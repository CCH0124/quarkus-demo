package entity;

import java.util.Objects;

public class Product {
    private String id;
    private Integer prices;
    private Integer itemsAvailable;

    public Product() {
    }

    public Product(String id, Integer prices, Integer itemsAvailable) {
        this.id = id;
        this.prices = prices;
        this.itemsAvailable = itemsAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrices() {
        return prices;
    }

    public void setPrices(Integer prices) {
        this.prices = prices;
    }

    public Integer getItemsAvailable() {
        return itemsAvailable;
    }

    public void setItemsAvailable(Integer itemsAvailable) {
        this.itemsAvailable = itemsAvailable;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", prices=" + prices +
                ", itemsAvailable=" + itemsAvailable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && prices.equals(product.prices) && itemsAvailable.equals(product.itemsAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prices, itemsAvailable);
    }
}
