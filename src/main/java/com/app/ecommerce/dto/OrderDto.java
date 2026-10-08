package com.app.ecommerce.dto;

import com.app.ecommerce.entity.Order;
import com.app.ecommerce.entity.Order.OrderStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    @Data
    public static class PlaceOrderRequest {
        @NotBlank(message = "Shipping address is required")
        private String shippingAddress;

		public PlaceOrderRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public PlaceOrderRequest(@NotBlank(message = "Shipping address is required") String shippingAddress) {
			super();
			this.shippingAddress = shippingAddress;
		}

		public String getShippingAddress() {
			return shippingAddress;
		}

		public void setShippingAddress(String shippingAddress) {
			this.shippingAddress = shippingAddress;
		}

		@Override
		public String toString() {
			return "PlaceOrderRequest [shippingAddress=" + shippingAddress + "]";
		}
        
        
    }

    @Data
    public static class UpdateStatusRequest {
        private Order.OrderStatus status;

		public UpdateStatusRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public UpdateStatusRequest(OrderStatus status) {
			super();
			this.status = status;
		}

		public Order.OrderStatus getStatus() {
			return status;
		}

		public void setStatus(Order.OrderStatus status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "UpdateStatusRequest [status=" + status + "]";
		}
        
        
    }

    @Data
    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal priceAtPurchase;
        private BigDecimal subtotal;
		public OrderItemResponse() {
			super();
			// TODO Auto-generated constructor stub
		}
		public OrderItemResponse(Long productId, String productName, Integer quantity, BigDecimal priceAtPurchase,
				BigDecimal subtotal) {
			super();
			this.productId = productId;
			this.productName = productName;
			this.quantity = quantity;
			this.priceAtPurchase = priceAtPurchase;
			this.subtotal = subtotal;
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
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public BigDecimal getPriceAtPurchase() {
			return priceAtPurchase;
		}
		public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
			this.priceAtPurchase = priceAtPurchase;
		}
		public BigDecimal getSubtotal() {
			return subtotal;
		}
		public void setSubtotal(BigDecimal subtotal) {
			this.subtotal = subtotal;
		}
		@Override
		public String toString() {
			return "OrderItemResponse [productId=" + productId + ", productName=" + productName + ", quantity="
					+ quantity + ", priceAtPurchase=" + priceAtPurchase + ", subtotal=" + subtotal + "]";
		}
        
        
        
    }

    @Data
    public static class OrderResponse {
        private Long id;
        private List<OrderItemResponse> items;
        private BigDecimal totalAmount;
        private String status;
        private String shippingAddress;
        private LocalDateTime orderedAt;
		public OrderResponse() {
			super();
			// TODO Auto-generated constructor stub
		}
		public OrderResponse(Long id, List<OrderItemResponse> items, BigDecimal totalAmount, String status,
				String shippingAddress, LocalDateTime orderedAt) {
			super();
			this.id = id;
			this.items = items;
			this.totalAmount = totalAmount;
			this.status = status;
			this.shippingAddress = shippingAddress;
			this.orderedAt = orderedAt;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public List<OrderItemResponse> getItems() {
			return items;
		}
		public void setItems(List<OrderItemResponse> items) {
			this.items = items;
		}
		public BigDecimal getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(BigDecimal totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getShippingAddress() {
			return shippingAddress;
		}
		public void setShippingAddress(String shippingAddress) {
			this.shippingAddress = shippingAddress;
		}
		public LocalDateTime getOrderedAt() {
			return orderedAt;
		}
		public void setOrderedAt(LocalDateTime orderedAt) {
			this.orderedAt = orderedAt;
		}
		@Override
		public String toString() {
			return "OrderResponse [id=" + id + ", items=" + items + ", totalAmount=" + totalAmount + ", status="
					+ status + ", shippingAddress=" + shippingAddress + ", orderedAt=" + orderedAt + "]";
		}
        
        
    }
}
