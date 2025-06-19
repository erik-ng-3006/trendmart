package com.example.trendmart.services.cart;

import com.example.trendmart.entities.Cart;
import com.example.trendmart.entities.CartItem;
import com.example.trendmart.entities.Product;
import com.example.trendmart.exceptions.ResourceNotFoundException;
import com.example.trendmart.repositories.ICartItemRepository;
import com.example.trendmart.repositories.ICartRepository;
import com.example.trendmart.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CartItemService  implements ICartItemService{
    private final ICartItemRepository cartItemRepository;
    private final ICartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // Use a synchronized block to prevent concurrent modifications
        synchronized (this) {
            // Get the cart with a fresh database state
            Cart cart = cartService.getCart(cartId);
            Product product = productService.getProductById(productId);

            // Check if the product is already in the cart
            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseGet(() -> {
                        CartItem newItem = new CartItem();
                        newItem.setCart(cart);
                        newItem.setProduct(product);
                        newItem.setQuantity(0);
                        newItem.setUnitPrice(product.getPrice());
                        return newItem;
                    });

            // Update quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice();

            // Save the cart item
            if (cartItem.getId() == null) {
                cart.addItem(cartItem);
            }
            
            cartItemRepository.save(cartItem);
            cartRepository.save(cart);
        }
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long cartId, Long itemId) {
        synchronized (this) {
            Cart cart = cartService.getCart(cartId);
            CartItem itemToRemove = getCartItem(cartId, itemId);
            cart.removeItem(itemToRemove);
            cartRepository.save(cart);
        }
    }

    @Override
    @Transactional
    public void updateItemQuantity(Long cartId, Long itemId, int quantity) {
        // Get the cart
        Cart cart = cartService.getCart(cartId);
        
        // Find the item in the cart
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));
        
        // Update item details
        item.setQuantity(quantity);
        item.setUnitPrice(item.getProduct().getPrice());
        item.setTotalPrice();
        
        // Save the updated item
        cartItemRepository.save(item);
        
        // Update cart total amount
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);

        return  cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
