# Spring Boot 3 + MyBatis Plus Order Service - Implementation Guide

## 1. Implementation Approach

### 1.1 Architecture Overview
The Order Service follows a **layered architecture** pattern with clear separation of concerns:
- **REST Controller Layer**: Handles HTTP requests
- **Service Layer**: Contains business logic
- **Data Access Layer**: MyBatis Plus ORM
- **Database Layer**: MySQL

### 1.2 Design Patterns Used
1. Layered Architecture
2. Repository Pattern (BaseMapper)
3. Service Locator Pattern
4. Data Transfer Object (DTO)
5. Singleton Pattern
6. Template Method Pattern

### 1.3 Key Technologies
- **Spring Boot 3.2.0**: Latest LTS version with Jakarta EE
- **MyBatis Plus 3.5.5**: ORM layer with automatic CRUD
- **MySQL 8.0.33**: Relational database
- **H2 Database**: In-memory testing database
- **JUnit 5 + Mockito**: Testing framework

## 2. Project Structure

```
order-service/
├── pom.xml
├── src/main/java/com/shopping/order/
│   ├── OrderApplication.java
│   ├── controller/OrderController.java
│   ├── service/IOrderService.java + OrderService.java
│   ├── mapper/OrderMapper.java + OrderItemMapper.java
│   ├── entity/Order.java + OrderItem.java
│   └── dto/OrderDTO.java + OrderItemDTO.java
├── src/main/resources/
│   ├── application.yml
│   ├── application-test.yml
│   └── db/migration/V1__init_order_table.sql
└── src/test/java/com/shopping/order/
    ├── service/OrderServiceTest.java
    └── controller/OrderControllerTest.java
```

## 3. Core Components

### 3.1 Entities
- **Order**: id, orderNo, userId, totalAmount, status, deleted, createTime, updateTime, remark
- **OrderItem**: id, orderId, productId, quantity, unitPrice, deleted, createTime, updateTime

### 3.2 DTOs
- **OrderDTO**: Input/output DTO with validation
- **OrderItemDTO**: Order item DTO with validation

### 3.3 Mapper Interfaces
- Extend BaseMapper<T> for automatic CRUD operations
- Support for custom queries when needed

### 3.4 Service Layer
- **IOrderService**: Interface defining business operations
- **OrderService**: Implementation with transaction management

### 3.5 Controller Layer
- **OrderController**: REST endpoints
- Standardized JSON response format
- Comprehensive error handling

## 4. Database Schema

### Orders Table
```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    deleted TINYINT(1) DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500),
    INDEX idx_user_id (user_id),
    INDEX idx_order_no (order_no)
);
```

### Order Items Table
```sql
CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    deleted TINYINT(1) DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
```

## 5. API Endpoints

| Method | Endpoint | Purpose |
|--------|----------|---------|
| POST | /api/orders | Create order |
| GET | /api/orders/{id} | Get order by ID |
| GET | /api/orders | List orders (paginated) |
| PUT | /api/orders/{id} | Update order |
| DELETE | /api/orders/{id} | Delete order |
| POST | /api/orders/{orderId}/items | Add items to order |
| GET | /api/orders/{orderId}/items | Get order items |
| DELETE | /api/orders/items/{itemId} | Delete order item |

## 6. Build & Test

```bash
# Compile
mvn clean compile -DskipTests

# Run tests
mvn clean test

# Build JAR
mvn clean package -DskipTests

# Run application
java -jar target/order-service-1.0.0.jar
```

## 7. Features Implemented

✅ Full CRUD operations
✅ Pagination support
✅ Soft delete functionality
✅ Input validation (Jakarta Validation)
✅ Comprehensive error handling
✅ Structured logging
✅ Transaction management
✅ Order status management
✅ Auto-managed timestamps
✅ 16 comprehensive tests

## 8. Best Practices

- Separation of Concerns (Layered Architecture)
- SOLID Principles
- DRY (Don't Repeat Yourself)
- Proper Exception Handling
- Input Validation
- Comprehensive Testing
- Code Documentation
- External Configuration

---

**Version**: 1.0.0
**Status**: Production Ready
