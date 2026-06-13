package com.shopping.order.service;

import com.shopping.order.dto.OrderItemDTO;
import com.shopping.order.mapper.OrderItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Order Service Unit Tests
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderService orderService;

    private OrderItemDTO testItemDTO;

    @BeforeEach
    void setUp() {
        testItemDTO = OrderItemDTO.builder()
            .orderId(1L)
            .productId(100L)
            .quantity(2)
            .unitPrice(new BigDecimal("100.00"))
            .build();
    }

    @Test
    void testGetOrderItems_Success() {
        when(orderItemMapper.selectList(any()))
            .thenReturn(new ArrayList<>());

        List<OrderItemDTO> result = orderService.getOrderItems(1L);

        assertNotNull(result);
        verify(orderItemMapper, times(1)).selectList(any());
    }

    @Test
    void testDeleteOrderItem_Success() {
        when(orderItemMapper.deleteById(1L)).thenReturn(1);

        boolean result = orderService.deleteOrderItem(1L);

        assertTrue(result);
        verify(orderItemMapper, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOrderItem_Failure() {
        when(orderItemMapper.deleteById(999L)).thenReturn(0);

        boolean result = orderService.deleteOrderItem(999L);

        assertFalse(result);
    }
}
