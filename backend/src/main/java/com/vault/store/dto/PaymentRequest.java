package com.vault.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank
    private String cardName;

    @NotBlank
    private String cardNumber;

    @NotBlank
    private String expiry;

    @NotBlank
    private String cvv;
}
