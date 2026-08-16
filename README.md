# VAULT Sneaker Store

A responsive sneaker shop with an HTML, CSS, and JavaScript storefront and a Java Spring Boot API.

## What you need on Windows

- Java 21
- Apache Maven
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

The easiest option on Windows is to open `index.html` in your browser:

1. Open the project folder in File Explorer.
2. Double-click `index.html`.
3. If Windows asks how to open it, choose Microsoft Edge or Chrome.

If you want to serve the folder over HTTP instead, and Python is installed, run this in Command Prompt from the project folder:

```bat
py -m http.server 5500
```

Then open `http://localhost:5500` in your browser.

The shop loads products from the API when it is running, and uses the local catalog otherwise.

## Demo card

Use `4242 4242 4242 4242` with any future expiry and a 3-digit CVV. Nothing is charged, and card numbers are not stored.
