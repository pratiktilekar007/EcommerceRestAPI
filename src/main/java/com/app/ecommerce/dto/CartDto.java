package com.app.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {

	@Data
	public static class AddToCartRequest {
		@NotNull(message = "Product ID is required")
		private Long productId;

		@Min(value = 1, message = "Quantity must be at least 1")
		private Integer quantity = 1;

		public AddToCartRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public AddToCartRequest(@NotNull(message = "Product ID is required") Long productId,
				@Min(value = 1, message = "Quantity must be at least 1") Integer quantity) {
			super();
			this.productId = productId;
			this.quantity = quantity;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return "AddToCartRequest [productId=" + productId + ", quantity=" + quantity + "]";
		}

	}

	@Data
	public static class UpdateQuantityRequest {
		@Min(value = 1, message = "Quantity must be at least 1")
		private Integer quantity;

		public UpdateQuantityRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public UpdateQuantityRequest(@Min(value = 1, message = "Quantity must be at least 1") Integer quantity) {
			super();
			this.quantity = quantity;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return "UpdateQuantityRequest [quantity=" + quantity + "]";
		}

	}

	@Data
	public static class CartItemResponse {
		private Long cartItemId;
		private Long productId;
		private String productName;
		private String imageUrl;
		private BigDecimal price;
		private Integer quantity;
		private BigDecimal subtotal;

		public CartItemResponse() {
			super();
			// TODO Auto-generated constructor stub
		}

		public CartItemResponse(Long cartItemId, Long productId, String productName, String imageUrl, BigDecimal price,
				Integer quantity, BigDecimal subtotal) {
			super();
			this.cartItemId = cartItemId;
			this.productId = productId;
			this.productName = productName;
			this.imageUrl = imageUrl;
			this.price = price;
			this.quantity = quantity;
			this.subtotal = subtotal;
		}

		public Long getCartItemId() {
			return cartItemId;
		}

		public void setCartItemId(Long cartItemId) {
			this.cartItemId = cartItemId;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public BigDecimal getSubtotal() {
			return subtotal;
		}

		public void setSubtotal(BigDecimal subtotal) {
			this.subtotal = subtotal;
		}

		@Override
		public String toString() {
			return "CartItemResponse [cartItemId=" + cartItemId + ", productId=" + productId + ", productName="
					+ productName + ", imageUrl=" + imageUrl + ", price=" + price + ", quantity=" + quantity
					+ ", subtotal=" + subtotal + "]";
		}

	}

	@Data
	public static class CartResponse {
		private List<CartItemResponse> items;
		private int totalItems;
		private BigDecimal totalAmount;

		public CartResponse() {
			super();
			// TODO Auto-generated constructor stub
		}

		public CartResponse(List<CartItemResponse> items, int totalItems, BigDecimal totalAmount) {
			super();
			this.items = items;
			this.totalItems = totalItems;
			this.totalAmount = totalAmount;
		}

		public List<CartItemResponse> getItems() {
			return items;
		}

		public void setItems(List<CartItemResponse> items) {
			this.items = items;
		}

		public int getTotalItems() {
			return totalItems;
		}

		public void setTotalItems(int totalItems) {
			this.totalItems = totalItems;
		}

		public BigDecimal getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(BigDecimal totalAmount) {
			this.totalAmount = totalAmount;
		}

		@Override
		public String toString() {
			return "CartResponse [items=" + items + ", totalItems=" + totalItems + ", totalAmount=" + totalAmount + "]";
		}

	}
}
