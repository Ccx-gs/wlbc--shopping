package com.shopping.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.order.dto.OrderDTO;
import com.shopping.order.dto.OrderItemDTO;
import com.shopping.order.service.IOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Order Controller Integration Tests
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDTO testOrderDTO;
    private OrderItemDTO testItemDTO;

    @BeforeEach
    void setUp() {
        testOrderDTO = OrderDTO.builder()
            .id(1L)
            .orderNo("ORD20240101001")
            .userId(1L)
            .totalAmount(new BigDecimal("999.99"))
            .status("PENDING")
            .remark("Test order")
            .createTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .build();

        testItemDTO = OrderItemDTO.builder()
            .id(1L)
            .orderId(1L)
            .productId(100L)
            .quantity(2)
            .unitPrice(new BigDecimal("100.00"))
            .createTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .build();
    }

    @Test
    void testCreateOrder_Success() throws Exception {
        when(orderService.createOrder(any(OrderDTO.class)))
            .thenReturn(testOrderDTO);

        MvcResult result = mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testOrderDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.data").exists())
            .andReturn();

        System.out.println("Create Order Response: " + result.getResponse().getContentAsString());
    }

    @Test
    void testGetOrder_Success() throws Exception {
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(testItemDTO);
        testOrderDTO.setItems(items);

        when(orderService.getOrderWithItems(1L))
            .thenReturn(testOrderDTO);

        mockMvc.perform(get("/api/orders/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.orderNo").value("ORD20240101001"));
    }

    @Test
    void testGetOrder_NotFound() throws Exception {
        when(orderService.getOrderWithItems(999L))
            .thenReturn(null);

        mockMvc.perform(get("/api/orders/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void testGetAllOrders_Success() throws Exception {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<OrderDTO> page = 
            new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 10);
        List<OrderDTO> records = new ArrayList<>();
        records.add(testOrderDTO);
        page.setRecords(records);
        page.setTotal(1);

        when(orderService.getAllOrders(1L, 10L))
            .thenReturn(page);

        mockMvc.perform(get("/api/orders")
                .param("pageNo", "1")
                .param("pageSize", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.total").value(1));
    }

    @Test
    void testUpdateOrder_Success() throws Exception {
        testOrderDTO.setStatus("PAID");

        when(orderService.updateOrder(eq(1L), any(OrderDTO.class)))
            .thenReturn(testOrderDTO);

        mockMvc.perform(put("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testOrderDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.status").value("PAID"));
    }

    @Test
    void testUpdateOrder_NotFound() throws Exception {
        when(orderService.updateOrder(eq(999L), any(OrderDTO.class)))
            .thenThrow(new RuntimeException("Order not found for ID: 999"));

        mockMvc.perform(put("/api/orders/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testOrderDTO)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void testDeleteOrder_Success() throws Exception {
        when(orderService.deleteOrder(1L))
            .thenReturn(true);

        mockMvc.perform(delete("/api/orders/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("Order deleted successfully"));
    }

    @Test
    void testDeleteOrder_NotFound() throws Exception {
        when(orderService.deleteOrder(999L))
            .thenReturn(false);

        mockMvc.perform(delete("/api/orders/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void testAddOrderItems_Success() throws Exception {
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(testItemDTO);

        when(orderService.addOrderItems(eq(1L), any(List.class)))
            .thenReturn(items);

        mockMvc.perform(post("/api/orders/1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testAddOrderItems_OrderNotFound() throws Exception {
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(testItemDTO);

        when(orderService.addOrderItems(eq(999L), any(List.class)))
            .thenThrow(new RuntimeException("Order not found for ID: 999"));

        mockMvc.perform(post("/api/orders/999/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void testGetOrderItems_Success() throws Exception {
        List<OrderItemDTO> items = new ArrayList<>();
        items.add(testItemDTO);

        when(orderService.getOrderItems(1L))
            .thenReturn(items);

        mockMvc.perform(get("/api/orders/1/items"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testDeleteOrderItem_Success() throws Exception {
        when(orderService.deleteOrderItem(1L))
            .thenReturn(true);

        mockMvc.perform(delete("/api/orders/items/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testDeleteOrderItem_NotFound() throws Exception {
        when(orderService.deleteOrderItem(999L))
            .thenReturn(false);

        mockMvc.perform(delete("/api/orders/items/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(404));
    }
}
