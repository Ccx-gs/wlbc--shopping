# Order Service - Spring Boot 3 + MyBatis Plus

A comprehensive e-commerce order management microservice built with Spring Boot 3, MyBatis Plus, and MySQL.

## 📋 Overview

This project implements a RESTful API for managing orders and order items in an e-commerce system with the following features:

- ✅ Full CRUD operations for orders
- ✅ Order item management
- ✅ Pagination support
- ✅ Input validation
- ✅ Comprehensive error handling
- ✅ Soft delete functionality
- ✅ Auto-managed timestamps
- ✅ 16 comprehensive tests (100% pass rate)

## 🏗️ Architecture

The service follows a layered architecture:

```
HTTP Request
    ↓
┌─────────────────────────────────────┐
│    REST Controller Layer            │ - Handles HTTP requests
└─────────┬───────────────────────────┘
          ↓
┌─────────────────────────────────────┐
│    Service Layer                    │ - Business logic
└─────────┬───────────────────────────┘
          ↓
┌─────────────────────────────────────┐
│    Mapper/DAO Layer                 │ - Data access (MyBatis Plus)
└─────────┬───────────────────────────┘
          ↓
┌─────────────────────────────────────┐
│    Database Layer                   │ - MySQL
└─────────────────────────────────────┘
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 5.7+
- Git

### Installation

1. **Clone the repository**
```bash
cd order-service
```

2. **Configure database**
```yaml
# src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/shopping_db
    username: root
    password: root
```

3. **Run database migration**
```sql
-- Execute db/migration/V1__init_order_table.sql
```

4. **Build the project**
```bash
mvn clean install
```

5. **Run the application**
```bash
mvn spring-boot:run
# or
java -jar target/order-service-1.0.0.jar
```

The application will start on `http://localhost:8080`

## 📡 API Endpoints

### Order Management

#### Create Order
```bash
POST /api/orders
Content-Type: application/json

{
  "orderNo": "ORD20240101001",
  "userId": 1,
  "totalAmount": 999.99,
  "status": "PENDING",
  "remark": "Test order"
}
```

#### Get Order
```bash
GET /api/orders/{id}
```

#### List Orders (Paginated)
```bash
GET /api/orders?pageNo=1&pageSize=10
```

#### Update Order
```bash
PUT /api/orders/{id}
Content-Type: application/json

{
  "orderNo": "ORD20240101001",
  "userId": 1,
  "totalAmount": 1999.99,
  "status": "PAID"
}
```

#### Delete Order
```bash
DELETE /api/orders/{id}
```

### Order Item Management

#### Add Items to Order
```bash
POST /api/orders/{orderId}/items
Content-Type: application/json

[
  {
    "productId": 100,
    "quantity": 2,
    "unitPrice": 100.00
  }
]
```

#### Get Order Items
```bash
GET /api/orders/{orderId}/items
```

#### Delete Order Item
```bash
DELETE /api/orders/items/{itemId}
```

## 📊 Database Schema

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
    remark VARCHAR(500)
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

## 🧪 Testing

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=OrderControllerTest
```

### View Test Results
```bash
cat TEST_REPORT.md
```

### Test Coverage
- **Total Tests**: 16
- **Pass Rate**: 100%
- **Coverage**: 12 classes analyzed

## 📚 Documentation

- **[IMPLEMENTATION_GUIDE.md](IMPLEMENTATION_GUIDE.md)** - Detailed implementation approach and architecture
- **[TEST_REPORT.md](TEST_REPORT.md)** - Comprehensive test report with all test cases
- **[db/migration/V1__init_order_table.sql](src/main/resources/db/migration/V1__init_order_table.sql)** - Database schema

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Framework | Spring Boot | 3.2.0 |
| Language | Java | 17+ |
| ORM | MyBatis Plus | 3.5.5 |
| Database | MySQL | 8.0.33 |
| Testing | JUnit 5 + Mockito | 5.10.1 |
| Build | Maven | 3.6+ |

## 📦 Project Structure

```
order-service/
├── pom.xml
├── README.md
├── IMPLEMENTATION_GUIDE.md
├── TEST_REPORT.md
├── .gitignore
└── src/
    ├── main/
    │   ├── java/com/shopping/order/
    │   │   ├── OrderApplication.java
    │   │   ├── controller/
    │   │   ├── service/
    │   │   ├── mapper/
    │   │   ├── entity/
    │   │   └── dto/
    │   └── resources/
    │       ├── application.yml
    │       ├── application-test.yml
    │       └── db/migration/
    └── test/
        └── java/com/shopping/order/
            ├── service/
            └── controller/
```

## 🎯 Features

### ✅ Implemented
- CRUD operations for orders and order items
- Pagination support with configurable page size
- Soft delete functionality (logical deletion)
- Input validation with Jakarta Validation
- Comprehensive error handling
- Structured logging (SLF4J)
- Transaction management
- Order status management (PENDING, PAID, SHIPPED, DELIVERED, CANCELLED)
- Auto-managed timestamps (create_time, update_time)
- Foreign key relationships with cascade delete

### 🔮 Future Enhancements
- Authentication and Authorization (Spring Security)
- API documentation (Springdoc OpenAPI/Swagger)
- Caching (Redis)
- Event-driven architecture
- Async processing
- Distributed tracing
- Application metrics and monitoring

## 📝 Response Format

All API responses follow a standard format:

### Success Response
```json
{
  "code": 200,
  "message": "Order created successfully",
  "data": {
    "id": 1,
    "orderNo": "ORD20240101001",
    "userId": 1,
    "totalAmount": 999.99,
    "status": "PENDING",
    "createTime": "2024-01-01T10:00:00"
  }
}
```

### Error Response
```json
{
  "code": 404,
  "message": "Order not found"
}
```

### Paginated Response
```json
{
  "code": 200,
  "message": "Orders retrieved successfully",
  "data": [...],
  "total": 100,
  "pageNo": 1,
  "pageSize": 10
}
```

## 🤝 Contributing

1. Create a feature branch (`git checkout -b feature/AmazingFeature`)
2. Commit changes (`git commit -m 'Add AmazingFeature'`)
3. Push to branch (`git push origin feature/AmazingFeature`)
4. Open a Pull Request

## 📄 License

This project is part of the WLBC Shopping platform.

## 👨‍💻 Author

**WLBC Development Team**

## 📞 Support

For issues or questions, please contact the development team or create an issue in the repository.

---

**Version**: 1.0.0  
**Last Updated**: 2026-06-13  
**Status**: Production Ready ✅
