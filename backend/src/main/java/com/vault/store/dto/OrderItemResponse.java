package com.vault.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

    private String productId;
    private String productName;
    private double size;
    private int quantity;
    private double unitPrice;
}
