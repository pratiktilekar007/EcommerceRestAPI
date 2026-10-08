package com.app.ecommerce.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();   // @Builder.Default → empty list

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;    // @Builder.Default → PENDING

    private String shippingAddress;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt = LocalDateTime.now(); // @Builder.Default → now

    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

    // ✅ Required by JPA
    public Order() {}

    // ✅ Private constructor used by Builder
    private Order(Builder builder) {
        this.user            = builder.user;
        this.items           = builder.items;
        this.totalAmount     = builder.totalAmount;
        this.status          = builder.status;
        this.shippingAddress = builder.shippingAddress;
        this.orderedAt       = builder.orderedAt;
        // id is NOT set — database auto-generates it
    }

    // ✅ Entry point
    public static Builder builder() {
        return new Builder();
    }

    // ✅ Getters
    public Long getId()                  { return id; }
    public User getUser()                { return user; }
    public List<OrderItem> getItems()    { return items; }
    public BigDecimal getTotalAmount()   { return totalAmount; }
    public OrderStatus getStatus()       { return status; }
    public String getShippingAddress()   { return shippingAddress; }
    public LocalDateTime getOrderedAt()  { return orderedAt; }

    // ✅ Setters
    public void setId(Long id)                           { this.id = id; }
    public void setUser(User user)                       { this.user = user; }
    public void setItems(List<OrderItem> items)          { this.items = items; }
    public void setTotalAmount(BigDecimal totalAmount)   { this.totalAmount = totalAmount; }
    public void setStatus(OrderStatus status)            { this.status = status; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public void setOrderedAt(LocalDateTime orderedAt)    { this.orderedAt = orderedAt; }

    // ============================
    //  BUILDER CLASS
    // ============================
    public static class Builder {

        private User user;
        private List<OrderItem> items = new ArrayList<>();        // @Builder.Default → empty list
        private BigDecimal totalAmount;
        private OrderStatus status = OrderStatus.PENDING;         // @Builder.Default → PENDING
        private String shippingAddress;
        private LocalDateTime orderedAt = LocalDateTime.now();    // @Builder.Default → now

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder items(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public Builder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder shippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public Builder orderedAt(LocalDateTime orderedAt) {
            this.orderedAt = orderedAt;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}