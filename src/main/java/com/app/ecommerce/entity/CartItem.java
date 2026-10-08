package com.app.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity = 1;   // @Builder.Default → 1

    // ✅ Required by JPA
    public CartItem() {}

    // ✅ Private constructor used by Builder
    private CartItem(Builder builder) {
        this.user     = builder.user;
        this.product  = builder.product;
        this.quantity = builder.quantity;
        // id is NOT set — database auto-generates it
    }

    // ✅ Entry point
    public static Builder builder() {
        return new Builder();
    }

    // ✅ Getters
    public Long getId()         { return id; }
    public User getUser()       { return user; }
    public Product getProduct() { return product; }
    public Integer getQuantity(){ return quantity; }

    // ✅ Setters
    public void setId(Long id)              { this.id = id; }
    public void setUser(User user)          { this.user = user; }
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(Integer quantity){ this.quantity = quantity; }

    // ============================
    //  BUILDER CLASS
    // ============================
    public static class Builder {

        private User user;
        private Product product;
        private Integer quantity = 1;   // @Builder.Default → 1

        public Builder user(User user) {
            this.user = user;
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

        public CartItem build() {
            return new CartItem(this);
        }
    }
}
