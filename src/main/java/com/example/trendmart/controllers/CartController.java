package com.example.trendmart.controllers;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.trendmart.entities.Cart;
import com.example.trendmart.exceptions.ResourceNotFoundException;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.services.cart.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Carts", description = "APIs for managing shopping carts")
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @Operation(summary = "Get cart by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cart found"),
        @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<CustomApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new CustomApiResponse("Success", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        }
    }

    @Operation(summary = "Clear cart by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cart cleared successfully"),
        @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<CustomApiResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new CustomApiResponse("Clear Cart Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        }
    }

    @Operation(summary = "Get total price of cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Total price calculated"),
        @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<CustomApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new CustomApiResponse("Total Price", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        }
    }
}