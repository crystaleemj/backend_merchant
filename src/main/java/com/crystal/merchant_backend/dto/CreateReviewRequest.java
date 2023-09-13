package com.crystal.merchant_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateReviewRequest {
    private double rating;
    private String feedback;
    private int userId;
    private int merchantId;
}
