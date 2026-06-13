package com.shopping.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shopping.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * Order Mapper
 * MyBatis Plus mapper for Order entity
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
