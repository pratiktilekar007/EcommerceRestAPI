package com.app.ecommerce.controller;

import com.app.ecommerce.dto.ApiResponse;
import com.app.ecommerce.dto.CartDto;
import com.app.ecommerce.entity.User;
import com.app.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Shopping cart endpoints")
@SecurityRequirement(name = "bearerAuth")
public class CartController {

    private final CartService cartService;
    
    public CartController(CartService cartService) {
    	this.cartService=cartService;
    }

    @GetMapping
    @Operation(summary = "View current user's cart")
    public ResponseEntity<ApiResponse<CartDto.CartResponse>> getCart(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(cartService.getCart(user)));
    }

    @PostMapping("/add")
    @Operation(summary = "Add a product to the cart")
    public ResponseEntity<ApiResponse<CartDto.CartResponse>> addToCart(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CartDto.AddToCartRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Item added to cart", cartService.addToCart(user, request)));
    }

    @PutMapping("/{cartItemId}")
    @Operation(summary = "Update item quantity in cart")
    public ResponseEntity<ApiResponse<CartDto.CartResponse>> updateQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartDto.UpdateQuantityRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cart updated", cartService.updateQuantity(user, cartItemId, request)));
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(summary = "Remove an item from the cart")
    public ResponseEntity<ApiResponse<CartDto.CartResponse>> removeItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId) {
        return ResponseEntity.ok(ApiResponse.success("Item removed from cart", cartService.removeItem(user, cartItemId)));
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Clear entire cart")
    public ResponseEntity<ApiResponse<Void>> clearCart(
            @AuthenticationPrincipal User user) {
        cartService.clearCart(user);
        return ResponseEntity.ok(ApiResponse.success("Cart cleared", null));
    }
}
