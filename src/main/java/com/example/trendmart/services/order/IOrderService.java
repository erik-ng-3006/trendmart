package com.example.trendmart.services.order;

import com.example.trendmart.dtos.OrderDTO;
import com.example.trendmart.entities.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);

    OrderDTO getOrder(Long orderId);

    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO convertToDto(Order order);
}
