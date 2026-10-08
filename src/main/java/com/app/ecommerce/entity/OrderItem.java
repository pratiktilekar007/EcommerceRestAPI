package com.app.ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal priceAtPurchase;

    // ✅ Required by JPA
    public OrderItem() {}

    // ✅ Private constructor used by Builder
    private OrderItem(Builder builder) {
        this.order           = builder.order;
        this.product         = builder.product;
        this.quantity        = builder.quantity;
        this.priceAtPurchase = builder.priceAtPurchase;
        // id is NOT set — database auto-generates it
    }

    // ✅ Entry point
    public static Builder builder() {
        return new Builder();
    }

    // ✅ Getters
    public Long getId()                    { return id; }
    public Order getOrder()                { return order; }
    public Product getProduct()            { return product; }
    public Integer getQuantity()           { return quantity; }
    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }

    // ✅ Setters
    public void setId(Long id)                           { this.id = id; }
    public void setOrder(Order order)                    { this.order = order; }
    public void setProduct(Product product)              { this.product = product; }
    public void setQuantity(Integer quantity)            { this.quantity = quantity; }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }

    // ============================
    //  BUILDER CLASS
    // ============================
    public static class Builder {

        private Order order;
        private Product product;
        private Integer quantity;
        private BigDecimal priceAtPurchase;

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder priceAtPurchase(BigDecimal priceAtPurchase) {
            this.priceAtPurchase = priceAtPurchase;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
