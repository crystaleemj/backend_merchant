package com.crystal.merchant_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crystal.merchant_backend.dto.UserCreationRequest;
import com.crystal.merchant_backend.entity.Merchant;
import com.crystal.merchant_backend.entity.Review;
import com.crystal.merchant_backend.entity.User;
import com.crystal.merchant_backend.repository.MerchantRepo;
import com.crystal.merchant_backend.repository.ReviewRepo;
import com.crystal.merchant_backend.repository.UserRepo;
import com.crystal.merchant_backend.service.MainService;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    MerchantRepo merchantRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ReviewRepo reviewRepo;

    @Override
    public Merchant getMerchantDetails(String merchantId) {
        return merchantRepo.listMerchantById(merchantId);
    }

    @Override
    public List<Merchant> getAllMerchantDetails() {
        return merchantRepo.listAllMerchants();
    }

    @Override
    public User getUserById(int id) {
        return userRepo.findUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public boolean createReview(Review review) {
        return reviewRepo.createReview(review);
    }

    @Override
    public boolean deleteReview(int reviewId) {
        return reviewRepo.deleteReview(reviewId);
    }

    @Override
    public List<Review> getAllReviewById(int merchantId) {
        return reviewRepo.getReviewsByMerchantId(merchantId);
    }

    @Override
    public Review getReviewById(int reviewId) {
        return reviewRepo.getReviewById(reviewId);
    }

    @Override
    public boolean createUser(UserCreationRequest userCreationRequest) {
        return userRepo.createUser(userCreationRequest);
    }

}
