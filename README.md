# 🛒 E-Commerce REST API — Spring Boot

A production-ready RESTful E-Commerce backend built with Spring Boot 3, Spring Security, JWT Authentication, and MySQL.

---

## 🚀 Tech Stack

| Layer        | Technology                          |
|--------------|-------------------------------------|
| Backend      | Spring Boot 3.2, Java 17            |
| Security     | Spring Security + JWT (jjwt 0.11.5) |
| Database     | MySQL + Spring Data JPA / Hibernate |
| Docs         | Swagger / OpenAPI 3                 |
| Build Tool   | Maven                               |
| Testing      | JUnit 5 + Mockito                   |

---

## 📁 Project Structure

```
src/main/java/com/app/ecommerce/
├── config/           → SecurityConfig, SwaggerConfig
├── controller/       → AuthController, ProductController, CartController, OrderController
├── dto/              → Request/Response DTOs
├── entity/           → JPA Entities (User, Product, CartItem, Order, OrderItem)
├── exception/        → GlobalExceptionHandler + Custom Exceptions
├── repository/       → Spring Data JPA Repositories
├── security/         → JwtUtil, JwtFilter
└── service/          → Business Logic Services
```

---

## ⚙️ Setup Instructions

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.8+

### 1. Clone and configure
```bash
git clone https://github.com/yourusername/ecommerce-springboot.git
cd ecommerce-springboot
```

### 2. Create MySQL database
```sql
CREATE DATABASE ecommerce_db;
```

### 3. Update `application.properties`
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 4. Run the application
```bash
mvn spring-boot:run
```

### 5. Access Swagger UI
```
http://localhost:8080/swagger-ui.html
```

---

## 🔐 Authentication Flow

1. **Register** → `POST /api/auth/register`
2. **Login** → `POST /api/auth/login` → receive JWT token
3. **Use token** → Add `Authorization: Bearer <token>` header to all protected requests

---

## 📡 API Endpoints

### Auth
| Method | Endpoint              | Description        | Auth Required |
|--------|-----------------------|--------------------|---------------|
| POST   | /api/auth/register    | Register new user  | ❌            |
| POST   | /api/auth/login       | Login & get token  | ❌            |

### Products
| Method | Endpoint                         | Description              | Auth Required |
|--------|----------------------------------|--------------------------|---------------|
| GET    | /api/products                    | List all products        | ❌            |
| GET    | /api/products?category=Electronics | Filter by category     | ❌            |
| GET    | /api/products?search=phone       | Search by name           | ❌            |
| GET    | /api/products/{id}               | Get product by ID        | ❌            |
| POST   | /api/products                    | Create product           | ✅ ADMIN      |
| PUT    | /api/products/{id}               | Update product           | ✅ ADMIN      |
| DELETE | /api/products/{id}               | Delete product           | ✅ ADMIN      |

### Cart
| Method | Endpoint              | Description              | Auth Required |
|--------|-----------------------|--------------------------|---------------|
| GET    | /api/cart             | View cart                | ✅ USER       |
| POST   | /api/cart/add         | Add item to cart         | ✅ USER       |
| PUT    | /api/cart/{id}        | Update item quantity     | ✅ USER       |
| DELETE | /api/cart/{id}        | Remove item from cart    | ✅ USER       |
| DELETE | /api/cart/clear       | Clear entire cart        | ✅ USER       |

### Orders
| Method | Endpoint               | Description              | Auth Required |
|--------|------------------------|--------------------------|---------------|
| POST   | /api/orders            | Place order from cart    | ✅ USER       |
| GET    | /api/orders            | Get my orders            | ✅ USER       |
| GET    | /api/orders/{id}       | Get order by ID          | ✅ USER       |
| PUT    | /api/orders/{id}/status| Update order status      | ✅ ADMIN      |

---

## 🧪 Sample Requests (Postman)

### Register
```json
POST /api/auth/register
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

### Login
```json
POST /api/auth/login
{
  "email": "john@example.com",
  "password": "password123"
}
```

### Add Product (Admin)
```json
POST /api/products
Authorization: Bearer <admin-token>
{
  "name": "iPhone 15",
  "description": "Latest Apple smartphone",
  "price": 79999.00,
  "stockQuantity": 50,
  "category": "Electronics",
  "imageUrl": "https://example.com/iphone15.jpg"
}
```

### Add to Cart
```json
POST /api/cart/add
Authorization: Bearer <user-token>
{
  "productId": 1,
  "quantity": 2
}
```

### Place Order
```json
POST /api/orders
Authorization: Bearer <user-token>
{
  "shippingAddress": "123 Main Street, Mumbai, Maharashtra 400001"
}
```

---

## 🗃️ Database Schema

```sql
users          → id, name, email, password, role, created_at
products       → id, name, description, price, stock_quantity, category, image_url, created_at
cart_items     → id, user_id, product_id, quantity
orders         → id, user_id, total_amount, status, shipping_address, ordered_at
order_items    → id, order_id, product_id, quantity, price_at_purchase
```

---

## 📌 Resume Highlights

- ✅ RESTful API design following best practices
- ✅ Stateless JWT-based authentication & authorization
- ✅ Role-based access control (ADMIN / USER)
- ✅ Clean layered architecture (Controller → Service → Repository)
- ✅ Global exception handling with meaningful error responses
- ✅ Input validation using Bean Validation (JSR-380)
- ✅ Interactive API documentation with Swagger UI
- ✅ Unit tests with JUnit 5 & Mockito

---

## 👨‍💻 Author

Built as a portfolio project to demonstrate Spring Boot REST API development skills.
