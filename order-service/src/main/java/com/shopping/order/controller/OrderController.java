package com.shopping.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shopping.order.dto.OrderDTO;
import com.shopping.order.dto.OrderItemDTO;
import com.shopping.order.service.IOrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Order REST Controller
 * Handles all order-related API requests
 */
@Slf4j
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create a new order
     * @param orderDTO Order DTO
     * @return Created order response
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.info("POST /api/orders - Creating order with order number: {}", orderDTO.getOrderNo());
        
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Order created successfully");
            response.put("data", createdOrder);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error creating order", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "Failed to create order: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Get order by ID with items
     * @param id Order ID
     * @return Order response
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrder(@PathVariable Long id) {
        log.info("GET /api/orders/{} - Getting order", id);
        
        try {
            OrderDTO order = orderService.getOrderWithItems(id);
            
            if (order == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 404);
                response.put("message", "Order not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Order retrieved successfully");
            response.put("data", order);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting order", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "Failed to get order: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Get all orders with pagination
     * @param pageNo Page number (default 1)
     * @param pageSize Page size (default 10)
     * @return Paginated orders response
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders(
            @RequestParam(defaultValue = "1") Long pageNo,
            @RequestParam(defaultValue = "10") Long pageSize) {
        log.info("GET /api/orders - Getting all orders - pageNo: {}, pageSize: {}", pageNo, pageSize);
        
        try {
            Page<OrderDTO> orders = orderService.getAllOrders(pageNo, pageSize);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Orders retrieved successfully");
            response.put("data", orders.getRecords());
            response.put("total", orders.getTotal());
            response.put("pageNo", pageNo);
            response.put("pageSize", pageSize);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting orders", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "Failed to get orders: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Update order
     * @param id Order ID
     * @param orderDTO Order DTO with updated data
     * @return Updated order response
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderDTO orderDTO) {
        log.info("PUT /api/orders/{} - Updating order", id);
        
        try {
            OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Order updated successfully");
            response.put("data", updatedOrder);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error updating order", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 404);
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("Error updating order", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "Failed to update order: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Delete order by ID
     * @param id Order ID
     * @return Delete response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long id) {
        log.info("DELETE /api/orders/{} - Deleting order", id);
        
        try {
            boolean deleted = orderService.deleteOrder(id);
            
            if (!deleted) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 404);
                response.put("message", "Order not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Order deleted successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error deleting order", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "Failed to delete order: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Add order items to an order
     * @param orderId Order ID
     * @param items List of order items
     * @return Added items response
     */
    @PostMapping("/{orderId}/items")
    public ResponseEntity<Map<String, Object>> addOrderItems(
            @PathVariable Long orderId,
            @Valid @RequestBody List<OrderItemDTO> items) {
        log.info("POST /api/orders/{}/items - Adding {} items", orderId, items.size());
        
        try {
            List<OrderItemDTO> addedItems = orderService.addOrderItems(orderId, items);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Order items added successfully");
            response.put("data", addedItems);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error adding order items", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 404);
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("Error adding order items", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "Failed to add order items: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Get order items by order ID
     * @param orderId Order ID
     * @return Order items response
     */
    @GetMapping("/{orderId}/items")
    public ResponseEntity<Map<String, Object>> getOrderItems(@PathVariable Long orderId) {
        log.info("GET /api/orders/{}/items - Getting order items", orderId);
        
        try {
            List<OrderItemDTO> items = orderService.getOrderItems(orderId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Order items retrieved successfully");
            response.put("data", items);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting order items", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "Failed to get order items: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Delete order item by ID
     * @param itemId Order item ID
     * @return Delete response
     */
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Map<String, Object>> deleteOrderItem(@PathVariable Long itemId) {
        log.info("DELETE /api/orders/items/{} - Deleting order item", itemId);
        
        try {
            boolean deleted = orderService.deleteOrderItem(itemId);
            
            if (!deleted) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 404);
                response.put("message", "Order item not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Order item deleted successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error deleting order item", e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "Failed to delete order item: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}
