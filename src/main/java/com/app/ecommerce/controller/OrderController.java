package com.app.ecommerce.controller;

import com.app.ecommerce.dto.ApiResponse;
import com.app.ecommerce.dto.OrderDto;
import com.app.ecommerce.entity.User;
import com.app.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
    	this.orderService=orderService;
    }

    @PostMapping
    @Operation(summary = "Place an order from current cart")
    public ResponseEntity<ApiResponse<OrderDto.OrderResponse>> placeOrder(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody OrderDto.PlaceOrderRequest request) {
        OrderDto.OrderResponse order = orderService.placeOrder(user, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order placed successfully", order));
    }

    @GetMapping
    @Operation(summary = "Get all orders of the current user")
    public ResponseEntity<ApiResponse<List<OrderDto.OrderResponse>>> getMyOrders(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getMyOrders(user)));
    }
    
    @GetMapping("/admin")
    @Operation(summary = "Get all orders for Admin user")
    public ResponseEntity<ApiResponse<List<OrderDto.OrderResponse>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrders()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific order by ID")
    public ResponseEntity<ApiResponse<OrderDto.OrderResponse>> getOrderById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(user, id)));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status (Admin only)")
    public ResponseEntity<ApiResponse<OrderDto.OrderResponse>> updateStatus(
            @PathVariable Long id,
            @RequestBody OrderDto.UpdateStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Order status updated", orderService.updateStatus(id, request)));
    }
}
