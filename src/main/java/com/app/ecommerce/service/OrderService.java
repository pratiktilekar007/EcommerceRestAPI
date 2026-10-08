package com.app.ecommerce.service;

import com.app.ecommerce.dto.OrderDto;
import com.app.ecommerce.entity.*;
import com.app.ecommerce.exception.BadRequestException;
import com.app.ecommerce.exception.ResourceNotFoundException;
import com.app.ecommerce.repository.OrderRepository;
import com.app.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;
    
    public OrderService(OrderRepository orderRepository, CartService cartService, ProductRepository productRepository) {
    	
    	this.orderRepository=orderRepository;
    	this.cartService=cartService;
    	this.productRepository=productRepository;
    }

    @Transactional
    public OrderDto.OrderResponse placeOrder(User user, OrderDto.PlaceOrderRequest request) {
        List<CartItem> cartItems = cartService.getCartItems(user);

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty. Add items before placing an order.");
        }

        // Validate stock and build order items
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }

            // Deduct stock
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(subtotal);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();

            orderItems.add(orderItem);
        }

        // Build and save order
        Order order = Order.builder()
                .user(user)
                .totalAmount(total)
                .shippingAddress(request.getShippingAddress())
                .status(Order.OrderStatus.PENDING)
                .build();

        orderItems.forEach(item -> item.setOrder(order));
        order.setItems(orderItems);

        Order saved = orderRepository.save(order);

        // Clear cart after order
        cartService.clearCart(user);

        return toResponse(saved);
    }

    public List<OrderDto.OrderResponse> getMyOrders(User user) {
        return orderRepository.findByUserOrderByOrderedAtDesc(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public List<OrderDto.OrderResponse> getOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrderDto.OrderResponse getOrderById(User user, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Order does not belong to this user");
        }

        return toResponse(order);
    }

    public OrderDto.OrderResponse updateStatus(Long orderId, OrderDto.UpdateStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        order.setStatus(request.getStatus());
        return toResponse(orderRepository.save(order));
    }

    private OrderDto.OrderResponse toResponse(Order order) {
        List<OrderDto.OrderItemResponse> itemResponses = order.getItems().stream().map(item -> {
            OrderDto.OrderItemResponse r = new OrderDto.OrderItemResponse();
            r.setProductId(item.getProduct().getId());
            r.setProductName(item.getProduct().getName());
            r.setQuantity(item.getQuantity());
            r.setPriceAtPurchase(item.getPriceAtPurchase());
            r.setSubtotal(item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())));
            return r;
        }).collect(Collectors.toList());

        OrderDto.OrderResponse response = new OrderDto.OrderResponse();
        response.setId(order.getId());
        response.setItems(itemResponses);
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        response.setShippingAddress(order.getShippingAddress());
        response.setOrderedAt(order.getOrderedAt());
        return response;
    }
}
