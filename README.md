# TrendMart E-commerce Platform

## 📋 Project Overview
TrendMart is a full-featured e-commerce platform built with Spring Boot, offering a complete online shopping experience. The application provides RESTful APIs for user management, product catalog, shopping cart, and order processing.

## ✨ Features

### User Management
- User registration and authentication
- User profile management
- Order history tracking

### Product Catalog
- Product browsing and search
- Product categorization
- Product images and details

### Shopping Cart
- Add/remove items from cart
- Update quantities
- Cart persistence across sessions

### Order Processing
- Secure checkout process
- Order history and tracking
- Order status updates

## 🛠 Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **Hibernate**
- **Maven**

### Database
- **MySQL 8.0+** (primary database)
- **H2** (for testing)

### API Documentation
- **Swagger/OpenAPI**
- **Postman** collection available

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6.3 or higher
- MySQL 8.0 or higher
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/trendmart.git
   cd trendmart
   ```

2. **Configure database**
   - Create a new MySQL database
   - Update `application.properties` with your database credentials (username, password, and database name)

3. **Build the application**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - API Documentation: `http://localhost:8080/swagger-ui.html`
   - Application: `http://localhost:8080`

## 📚 API Documentation

### Authentication
- `POST /api/v1/auth/register` - Register a new user
- `POST /api/v1/auth/login` - Authenticate user

### Users
- `GET /api/v1/users/{userId}` - Get user details
- `PUT /api/v1/users/{userId}` - Update user profile
- `DELETE /api/v1/users/{userId}` - Delete user account

### Products
- `GET /api/v1/products` - Get all products
- `GET /api/v1/products/{productId}` - Get product details
- `POST /api/v1/products` - Add new product (Admin only)
- `PUT /api/v1/products/{productId}` - Update product (Admin only)
- `DELETE /api/v1/products/{productId}` - Delete product (Admin only)

### Cart
- `GET /api/v1/cart` - Get user's cart
- `POST /api/v1/cart/items` - Add item to cart
- `PUT /api/v1/cart/items/{itemId}` - Update cart item quantity
- `DELETE /api/v1/cart/items/{itemId}` - Remove item from cart

### Orders
- `GET /api/v1/orders` - Get user's orders
- `POST /api/v1/orders` - Create new order
- `GET /api/v1/orders/{orderId}` - Get order details

## 🗄 Database Schema

The application uses the following main entities:

- **User** - Stores user information
- **Product** - Product catalog
- **Category** - Product categories
- **Cart** - Shopping cart
- **CartItem** - Items in the cart
- **Order** - Order information
- **OrderItem** - Items in an order

## 🔒 Environment Variables

Create a `.env` file in the root directory with the following variables:

```
# Database
DB_URL=jdbc:mysql://localhost:3306/trendmart?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION_MS=86400000
```

## 🧪 Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=YourTestClass

# Run with coverage
mvn jacoco:report
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Hibernate](https://hibernate.org/)
- [MySQL](https://www.mysql.com/)
- [Maven](https://maven.apache.org/)
