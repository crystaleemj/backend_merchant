package com.crystal.merchant_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateMerchantRequest {
    private String merchantName;
    private String category;
    private String imageUrl;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String merchantDesc;
}
