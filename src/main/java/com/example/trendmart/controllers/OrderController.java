package com.example.trendmart.controllers;

import java.util.List;

import com.example.trendmart.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.example.trendmart.dtos.OrderDTO;
import com.example.trendmart.entities.Order;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.services.order.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Orders", description = "APIs for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @Operation(summary = "Create a new order from user's cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order created successfully"),
        @ApiResponse(responseCode = "404", description = "User or cart not found")
    })
    @PostMapping("/order")
    public ResponseEntity<CustomApiResponse> createOrder(@Parameter(description = "ID of the user") @RequestParam Long userId) {
        try {
            Order order =  orderService.placeOrder(userId);
            OrderDTO orderDTO = orderService.convertToDto(order);
            return ResponseEntity.ok(new CustomApiResponse("Item Order Success!", order));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomApiResponse("Error Occured!", e.getMessage()));
        }
    }

    @Operation(summary = "Get order by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/order/{orderId}")
    public ResponseEntity<CustomApiResponse> getOrderById(
            @Parameter(description = "ID of the order") @PathVariable Long orderId) {
        try {
            OrderDTO order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new CustomApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomApiResponse("Oops!", e.getMessage()));
        }
    }

    @Operation(summary = "Get all orders for a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<CustomApiResponse> getUserOrders(
            @Parameter(description = "ID of the user") @PathVariable Long userId) {
        try {
            List<OrderDTO> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new CustomApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomApiResponse("Oops!", e.getMessage()));
        }
    }
}
