package com.shopping.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.order.dto.OrderDTO;
import com.shopping.order.dto.OrderItemDTO;
import com.shopping.order.entity.Order;
import java.util.List;

/**
 * Order Service Interface
 */
public interface IOrderService extends IService<Order> {

    /**
     * Create a new order
     * @param orderDTO Order DTO
     * @return Created order DTO
     */
    OrderDTO createOrder(OrderDTO orderDTO);

    /**
     * Get order by ID with items
     * @param id Order ID
     * @return Order DTO with items
     */
    OrderDTO getOrderWithItems(Long id);

    /**
     * Get all orders with pagination
     * @param pageNo Page number
     * @param pageSize Page size
     * @return Page of orders
     */
    Page<OrderDTO> getAllOrders(Long pageNo, Long pageSize);

    /**
     * Update order
     * @param id Order ID
     * @param orderDTO Order DTO
     * @return Updated order DTO
     */
    OrderDTO updateOrder(Long id, OrderDTO orderDTO);

    /**
     * Delete order by ID
     * @param id Order ID
     * @return true if deleted, false otherwise
     */
    boolean deleteOrder(Long id);

    /**
     * Add order items
     * @param orderId Order ID
     * @param items List of order item DTOs
     * @return List of added order item DTOs
     */
    List<OrderItemDTO> addOrderItems(Long orderId, List<OrderItemDTO> items);

    /**
     * Get order items by order ID
     * @param orderId Order ID
     * @return List of order item DTOs
     */
    List<OrderItemDTO> getOrderItems(Long orderId);

    /**
     * Delete order item by ID
     * @param itemId Order item ID
     * @return true if deleted, false otherwise
     */
    boolean deleteOrderItem(Long itemId);
}
