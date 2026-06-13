package com.shopping.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order Item Entity
 * Represents items within an order
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("order_items")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Order Item ID (Primary Key)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Order ID (Foreign Key)
     */
    private Long orderId;

    /**
     * Product ID
     */
    private Long productId;

    /**
     * Quantity
     */
    private Integer quantity;

    /**
     * Unit Price
     */
    private BigDecimal unitPrice;

    /**
     * Logic Delete Flag
     */
    @TableLogic
    private Integer deleted;

    /**
     * Create Time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * Update Time
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
