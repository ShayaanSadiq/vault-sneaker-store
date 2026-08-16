# VAULT Sneaker Store

A responsive sneaker shop with an HTML, CSS, and JavaScript storefront and a Java Spring Boot API.

## Prerequisites

- Java 21
- Apache Maven
- Visual Studio Code (or Cursor)
- The **Live Server** extension by Ritwick Dey
- Microsoft Edge, Chrome, or Firefox

## Pages

- `index.html` — landing page
- `products.html` — product catalog
- `cart.html` — shopping bag
- `payment.html` — checkout with form validation

## Run the API

In Command Prompt or PowerShell:

```bat
cd backend
mvn spring-boot:run
```

The API listens on `http://localhost:8080`.

- `GET /api/products`
- `GET /api/products/{id}`
- `POST /api/orders`
- `GET /api/orders/{id}`

Keep that window open while you use the shop.

## Run the storefront

Use Live Server in VS Code (or Cursor) so the pages run over HTTP:

1. Open this project folder in VS Code.
2. Install **Live Server** from the Extensions view if it is not already installed (`Ctrl+Shift+X`, search for Live Server).
3. Right-click `index.html` in the Explorer.
4. Choose **Open with Live Server**.

The site usually opens at `http://127.0.0.1:5500`. Keep Live Server running while you browse Home, Shop, Cart, and Payment.

The shop loads products from the API when it is running, and uses the local catalog otherwise.

## Demo card

Use `4242 4242 4242 4242` with any future expiry and a 3-digit CVV. Nothing is charged, and card numbers are not stored.
