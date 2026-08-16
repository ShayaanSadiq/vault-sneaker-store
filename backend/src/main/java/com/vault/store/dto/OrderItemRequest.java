package com.vault.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotBlank
    private String productId;

    @NotNull
    private Double size;

    @Min(1)
    private int quantity;
}
