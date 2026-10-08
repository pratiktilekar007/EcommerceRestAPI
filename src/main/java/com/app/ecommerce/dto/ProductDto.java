package com.app.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {

	@Data
	public static class Request {
		@NotBlank(message = "Product name is required")
		private String name;

		private String description;

		@NotNull(message = "Price is required")
		@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
		private BigDecimal price;

		@Min(value = 0, message = "Stock cannot be negative")
		private Integer stockQuantity = 0;

		private String category;

		private String imageUrl;

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Request(@NotBlank(message = "Product name is required") String name, String description,
				@NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") BigDecimal price,
				@Min(value = 0, message = "Stock cannot be negative") Integer stockQuantity, String category,
				String imageUrl) {
			super();
			this.name = name;
			this.description = description;
			this.price = price;
			this.stockQuantity = stockQuantity;
			this.category = category;
			this.imageUrl = imageUrl;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public Integer getStockQuantity() {
			return stockQuantity;
		}

		public void setStockQuantity(Integer stockQuantity) {
			this.stockQuantity = stockQuantity;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		@Override
		public String toString() {
			return "Request [name=" + name + ", description=" + description + ", price=" + price + ", stockQuantity="
					+ stockQuantity + ", category=" + category + ", imageUrl=" + imageUrl + "]";
		}

	}

	@Data
	public static class Response {
		private Long id;
		private String name;
		private String description;
		private BigDecimal price;
		private Integer stockQuantity;
		private String category;
		private String imageUrl;
		private LocalDateTime createdAt;

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Response(Long id, String name, String description, BigDecimal price, Integer stockQuantity,
				String category, String imageUrl, LocalDateTime createdAt) {
			super();
			this.id = id;
			this.name = name;
			this.description = description;
			this.price = price;
			this.stockQuantity = stockQuantity;
			this.category = category;
			this.imageUrl = imageUrl;
			this.createdAt = createdAt;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public Integer getStockQuantity() {
			return stockQuantity;
		}

		public void setStockQuantity(Integer stockQuantity) {
			this.stockQuantity = stockQuantity;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		@Override
		public String toString() {
			return "Response [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
					+ ", stockQuantity=" + stockQuantity + ", category=" + category + ", imageUrl=" + imageUrl
					+ ", createdAt=" + createdAt + "]";
		}

	}
}
