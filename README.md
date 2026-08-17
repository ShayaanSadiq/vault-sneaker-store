# VAULT Sneaker Store

A responsive sneaker shop with an HTML, CSS, and JavaScript storefront and a Java Spring Boot API.

## Prerequisites

- Java 21
- Apache Maven
- Microsoft Edge, Chrome, or Firefox

## Pages

- `frontend/index.html` — landing page
- `frontend/products.html` — product catalog
- `frontend/cart.html` — shopping bag
- `frontend/payment.html` — checkout with form validation

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

Open `frontend/index.html` in your browser.

The shop loads products from the API when it is running, and uses the local catalog otherwise.
