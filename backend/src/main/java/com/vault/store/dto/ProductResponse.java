package com.vault.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private String id;
    private String name;
    private String brand;
    private double price;
    private String category;
    private String badge;
    private String description;
    private String image;
}
