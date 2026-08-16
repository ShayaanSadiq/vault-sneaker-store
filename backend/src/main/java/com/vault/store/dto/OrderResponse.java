package com.vault.store.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private String id;
    private String customerName;
    private String email;
    private String status;
    private double subtotal;
    private double shipping;
    private double tax;
    private double total;
    private Instant createdAt;
    private List<OrderItemResponse> items;
}
