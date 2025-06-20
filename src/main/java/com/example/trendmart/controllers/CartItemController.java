package com.example.trendmart.controllers;

import static org.ietf.jgss.GSSException.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.trendmart.entities.User;
import com.example.trendmart.exceptions.ResourceNotFoundException;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.services.cart.ICartItemService;
import com.example.trendmart.services.cart.ICartService;
import com.example.trendmart.services.user.IUserService;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart Items", description = "APIs for managing cart items")
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @Operation(summary = "Add item to cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item added to cart successfully"),
        @ApiResponse(responseCode = "404", description = "Cart or product not found")
    })
    @PostMapping("/item/add")
    public ResponseEntity<CustomApiResponse> addItemToCart(
            @Parameter(description = "Cart ID (optional, will create new cart if not provided)") @RequestParam(required = false) Long cartId,
            @Parameter(description = "ID of the product to add") @RequestParam Long productId,
            @Parameter(description = "Quantity of the product") @RequestParam Integer quantity) {
        try {
            User user = userService.getAuthenticatedUser();

            if (cartId == null) {
                cartId = cartService.initializeNewCart(user);
            }
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new CustomApiResponse("Add Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new CustomApiResponse(e.getMessage(), null));
        }
    }

    @Operation(summary = "Remove item from cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item removed from cart"),
        @ApiResponse(responseCode = "404", description = "Cart or item not found")
    })
    @DeleteMapping("/{cartId}/item/{itemId}/remove")
    public ResponseEntity<CustomApiResponse> removeItemFromCart(
            @Parameter(description = "ID of the cart") @PathVariable Long cartId,
            @Parameter(description = "ID of the item to remove") @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new CustomApiResponse("Remove Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        }
    }

    @Operation(summary = "Update item quantity in cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item quantity updated"),
        @ApiResponse(responseCode = "404", description = "Cart or item not found")
    })
    @PutMapping("/{cartId}/item/{itemId}/update-quantity")
    public ResponseEntity<CustomApiResponse> updateItemQuantity(
            @Parameter(description = "ID of the cart") @PathVariable Long cartId,
            @Parameter(description = "ID of the item to update") @PathVariable Long itemId,
            @Parameter(description = "New quantity for the item") @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new CustomApiResponse("Update Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        }

    }
}
