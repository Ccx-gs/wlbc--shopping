package com.shopping.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.order.dto.OrderDTO;
import com.shopping.order.dto.OrderItemDTO;
import com.shopping.order.entity.Order;
import com.shopping.order.entity.OrderItem;
import com.shopping.order.mapper.OrderItemMapper;
import com.shopping.order.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Order Service Implementation
 */
@Slf4j
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final OrderItemMapper orderItemMapper;

    public OrderService(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        log.info("Creating order with order number: {}", orderDTO.getOrderNo());

        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setStatus("PENDING");
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        boolean saved = this.save(order);
        if (!saved) {
            throw new RuntimeException("Failed to create order");
        }

        OrderDTO result = new OrderDTO();
        BeanUtils.copyProperties(order, result);
        log.info("Order created successfully with ID: {}", order.getId());
        return result;
    }

    @Override
    public OrderDTO getOrderWithItems(Long id) {
        log.info("Getting order with items for ID: {}", id);

        Order order = this.getById(id);
        if (order == null) {
            log.warn("Order not found for ID: {}", id);
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);

        List<OrderItem> items = orderItemMapper.selectList(
            new QueryWrapper<OrderItem>().eq("order_id", id)
        );
        
        if (!items.isEmpty()) {
            List<OrderItemDTO> itemDTOs = items.stream()
                .map(item -> {
                    OrderItemDTO itemDTO = new OrderItemDTO();
                    BeanUtils.copyProperties(item, itemDTO);
                    return itemDTO;
                })
                .collect(Collectors.toList());
            orderDTO.setItems(itemDTOs);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> getAllOrders(Long pageNo, Long pageSize) {
        log.info("Getting all orders - pageNo: {}, pageSize: {}", pageNo, pageSize);

        Page<Order> page = new Page<>(pageNo, pageSize);
        Page<Order> orderPage = this.page(page);

        Page<OrderDTO> dtoPage = new Page<>(pageNo, pageSize, orderPage.getTotal());
        List<OrderDTO> records = orderPage.getRecords().stream()
            .map(order -> {
                OrderDTO dto = new OrderDTO();
                BeanUtils.copyProperties(order, dto);
                return dto;
            })
            .collect(Collectors.toList());
        dtoPage.setRecords(records);

        return dtoPage;
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        log.info("Updating order with ID: {}", id);

        Order order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("Order not found for ID: " + id);
        }

        order.setOrderNo(orderDTO.getOrderNo());
        order.setUserId(orderDTO.getUserId());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());
        order.setRemark(orderDTO.getRemark());
        order.setUpdateTime(LocalDateTime.now());

        boolean updated = this.updateById(order);
        if (!updated) {
            throw new RuntimeException("Failed to update order");
        }

        OrderDTO result = new OrderDTO();
        BeanUtils.copyProperties(order, result);
        log.info("Order updated successfully for ID: {}", id);
        return result;
    }

    @Override
    @Transactional
    public boolean deleteOrder(Long id) {
        log.info("Deleting order with ID: {}", id);

        Order order = this.getById(id);
        if (order == null) {
            log.warn("Order not found for ID: {}", id);
            return false;
        }

        boolean deleted = this.removeById(id);
        if (deleted) {
            log.info("Order deleted successfully for ID: {}", id);
        } else {
            log.warn("Failed to delete order for ID: {}", id);
        }
        return deleted;
    }

    @Override
    @Transactional
    public List<OrderItemDTO> addOrderItems(Long orderId, List<OrderItemDTO> items) {
        log.info("Adding {} items to order ID: {}", items.size(), orderId);

        Order order = this.getById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found for ID: " + orderId);
        }

        List<OrderItem> orderItems = items.stream()
            .map(itemDTO -> {
                OrderItem item = new OrderItem();
                BeanUtils.copyProperties(itemDTO, item);
                item.setOrderId(orderId);
                item.setCreateTime(LocalDateTime.now());
                item.setUpdateTime(LocalDateTime.now());
                return item;
            })
            .collect(Collectors.toList());

        boolean saved = orderItemMapper.insert(orderItems.get(0)) > 0;
        if (!saved && !orderItems.isEmpty()) {
            throw new RuntimeException("Failed to add order items");
        }

        return items;
    }

    @Override
    public List<OrderItemDTO> getOrderItems(Long orderId) {
        log.info("Getting items for order ID: {}", orderId);

        List<OrderItem> items = orderItemMapper.selectList(
            new QueryWrapper<OrderItem>().eq("order_id", orderId)
        );

        return items.stream()
            .map(item -> {
                OrderItemDTO dto = new OrderItemDTO();
                BeanUtils.copyProperties(item, dto);
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean deleteOrderItem(Long itemId) {
        log.info("Deleting order item with ID: {}", itemId);
        return orderItemMapper.deleteById(itemId) > 0;
    }
}
