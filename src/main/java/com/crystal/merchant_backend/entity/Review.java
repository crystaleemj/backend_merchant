package com.crystal.merchant_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Review {
    private int reviewId;
    private double rating;
    private String feedback;
    private String imageUrl;
    private int userId;
    private int merchantId;
}
