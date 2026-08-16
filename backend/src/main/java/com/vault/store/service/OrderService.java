package com.vault.store.service;

import com.vault.store.domain.CustomerOrder;
import com.vault.store.domain.OrderItem;
import com.vault.store.domain.Product;
import com.vault.store.dto.CreateOrderRequest;
import com.vault.store.dto.OrderItemRequest;
import com.vault.store.dto.OrderItemResponse;
import com.vault.store.dto.OrderResponse;
import com.vault.store.dto.PaymentRequest;
import com.vault.store.repository.OrderRepository;
import com.vault.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    public static final double SHIPPING = 12.0;
    public static final double TAX_RATE = 0.08;

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        validatePayment(request.getPayment());

        List<OrderItem> items = new ArrayList<>();
        double subtotal = 0;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("Unknown product: " + itemRequest.getProductId()));
            subtotal += product.getPrice() * itemRequest.getQuantity();
            items.add(OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .size(itemRequest.getSize())
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(product.getPrice())
                    .build());
        }

        double tax = round(subtotal * TAX_RATE);
        double total = round(subtotal + SHIPPING + tax);
        String orderId = "VLT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        CustomerOrder order = CustomerOrder.builder()
                .id(orderId)
                .customerName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .zip(request.getZip())
                .subtotal(round(subtotal))
                .shipping(SHIPPING)
                .tax(tax)
                .total(total)
                .status("PAID")
                .createdAt(Instant.now())
                .build();

        items.forEach(item -> item.setOrder(order));
        order.setItems(items);

        return toResponse(orderRepository.save(order));
    }

    public OrderResponse getOrder(String id) {
        return orderRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
    }

    void validatePayment(PaymentRequest payment) {
        String number = payment.getCardNumber().replaceAll("\\D", "");
        if (number.length() != 16 || !luhnCheck(number)) {
            throw new IllegalArgumentException("Enter a valid 16-digit card number.");
        }

        String cvv = payment.getCvv().replaceAll("\\D", "");
        if (cvv.length() < 3 || cvv.length() > 4) {
            throw new IllegalArgumentException("Enter a 3 or 4 digit CVV.");
        }

        try {
            YearMonth expiry = YearMonth.parse(payment.getExpiry(), DateTimeFormatter.ofPattern("MM/yy"));
            if (!expiry.isAfter(YearMonth.now())) {
                throw new IllegalArgumentException("This card is expired.");
            }
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Use MM/YY expiry format.");
        }
    }

    private boolean luhnCheck(String number) {
        int sum = 0;
        boolean shouldDouble = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (shouldDouble) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            shouldDouble = !shouldDouble;
        }
        return sum % 10 == 0;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private OrderResponse toResponse(CustomerOrder order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .email(order.getEmail())
                .status(order.getStatus())
                .subtotal(order.getSubtotal())
                .shipping(order.getShipping())
                .tax(order.getTax())
                .total(order.getTotal())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(item -> OrderItemResponse.builder()
                                .productId(item.getProductId())
                                .productName(item.getProductName())
                                .size(item.getSize())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .build())
                        .toList())
                .build();
    }
}
