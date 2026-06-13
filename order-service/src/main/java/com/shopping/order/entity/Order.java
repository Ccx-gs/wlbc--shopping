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
 * Order Entity
 * Represents an order in the e-commerce system
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Order ID (Primary Key)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Order Number (Unique)
     */
    private String orderNo;

    /**
     * User ID
     */
    private Long userId;

    /**
     * Total Amount
     */
    private BigDecimal totalAmount;

    /**
     * Order Status: PENDING, PAID, SHIPPED, DELIVERED, CANCELLED
     */
    private String status;

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

    /**
     * Remark
     */
    private String remark;
}
