package com.app.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stockQuantity = 0;        // default = 0

    private String category;

    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();  // default = now

    // ✅ Required by JPA
    public Product() {}

    // ✅ Private constructor used by Builder
    private Product(Builder builder) {
        this.name          = builder.name;
        this.description   = builder.description;
        this.price         = builder.price;
        this.stockQuantity = builder.stockQuantity;
        this.category      = builder.category;
        this.imageUrl      = builder.imageUrl;
        this.createdAt     = builder.createdAt;
        // id is NOT set here — database auto-generates it
    }

    // ✅ Entry point
    public static Builder builder() {
        return new Builder();
    }

    // ✅ Getters (required by JPA)
    public Long getId()                  { return id; }
    public String getName()              { return name; }
    public String getDescription()       { return description; }
    public BigDecimal getPrice()         { return price; }
    public Integer getStockQuantity()    { return stockQuantity; }
    public String getCategory()          { return category; }
    public String getImageUrl()          { return imageUrl; }
    public LocalDateTime getCreatedAt()  { return createdAt; }

    // ✅ Setters (required by JPA & updates)
    public void setId(Long id)                        { this.id = id; }
    public void setName(String name)                  { this.name = name; }
    public void setDescription(String description)    { this.description = description; }
    public void setPrice(BigDecimal price)            { this.price = price; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    public void setCategory(String category)          { this.category = category; }
    public void setImageUrl(String imageUrl)          { this.imageUrl = imageUrl; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ============================
    //  BUILDER CLASS
    // ============================
    public static class Builder {

        private String name;
        private String description;
        private BigDecimal price;
        private Integer stockQuantity = 0;               // @Builder.Default → 0
        private String category;
        private String imageUrl;
        private LocalDateTime createdAt = LocalDateTime.now(); // @Builder.Default → now

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder stockQuantity(Integer stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
