package com.app.ecommerce.service;

import com.app.ecommerce.dto.CartDto;
import com.app.ecommerce.entity.CartItem;
import com.app.ecommerce.entity.Product;
import com.app.ecommerce.entity.User;
import com.app.ecommerce.exception.BadRequestException;
import com.app.ecommerce.exception.ResourceNotFoundException;
import com.app.ecommerce.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    
    public CartService(CartItemRepository cartItemRepository, ProductService productService) {
    	this.cartItemRepository=cartItemRepository;
    	this.productService=productService;
    }

    public CartDto.CartResponse getCart(User user) {
        List<CartItem> items = cartItemRepository.findByUser(user);
        return buildCartResponse(items);
    }

    public CartDto.CartResponse addToCart(User user, CartDto.AddToCartRequest request) {
        Product product = productService.findProductById(request.getProductId());

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new BadRequestException("Insufficient stock. Available: " + product.getStockQuantity());
        }

        CartItem cartItem = cartItemRepository
                .findByUserAndProductId(user, request.getProductId())
                .orElse(CartItem.builder().user(user).product(product).quantity(0).build());

        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        cartItemRepository.save(cartItem);

        return getCart(user);
    }

    public CartDto.CartResponse updateQuantity(User user, Long cartItemId, CartDto.UpdateQuantityRequest request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Cart item does not belong to this user");
        }

        if (cartItem.getProduct().getStockQuantity() < request.getQuantity()) {
            throw new BadRequestException("Insufficient stock");
        }

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return getCart(user);
    }

    public CartDto.CartResponse removeItem(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Cart item does not belong to this user");
        }

        cartItemRepository.delete(cartItem);
        return getCart(user);
    }

    public void clearCart(User user) {
        cartItemRepository.deleteByUser(user);
    }

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    private CartDto.CartResponse buildCartResponse(List<CartItem> items) {
        List<CartDto.CartItemResponse> itemResponses = items.stream().map(item -> {
            CartDto.CartItemResponse r = new CartDto.CartItemResponse();
            r.setCartItemId(item.getId());
            r.setProductId(item.getProduct().getId());
            r.setProductName(item.getProduct().getName());
            r.setImageUrl(item.getProduct().getImageUrl());
            r.setPrice(item.getProduct().getPrice());
            r.setQuantity(item.getQuantity());
            r.setSubtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return r;
        }).collect(Collectors.toList());

        BigDecimal total = itemResponses.stream()
                .map(CartDto.CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartDto.CartResponse response = new CartDto.CartResponse();
        response.setItems(itemResponses);
        response.setTotalItems(items.size());
        response.setTotalAmount(total);
        return response;
    }
}
