package com.shopping.order.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order DTO for API requests and responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;

    @NotBlank(message = "Order number cannot be empty")
    private String orderNo;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Total amount cannot be null")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    @NotBlank(message = "Status cannot be empty")
    private String status;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<OrderItemDTO> items;

    /**
     * Order Status Enum
     */
    public enum OrderStatus {
        PENDING("PENDING", "待支付"),
        PAID("PAID", "已支付"),
        SHIPPED("SHIPPED", "已发货"),
        DELIVERED("DELIVERED", "已收货"),
        CANCELLED("CANCELLED", "已取消");

        private final String code;
        private final String description;

        OrderStatus(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}
