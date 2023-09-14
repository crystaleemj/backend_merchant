package com.crystal.merchant_backend.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.crystal.merchant_backend.dto.CreateMerchantRequest;
import com.crystal.merchant_backend.dto.UserAuthRequest;
import com.crystal.merchant_backend.dto.UserConfirmPassword;
import com.crystal.merchant_backend.dto.UserCreationRequest;
import com.crystal.merchant_backend.entity.Merchant;
import com.crystal.merchant_backend.entity.Review;
import com.crystal.merchant_backend.entity.User;

@Service
public interface MainService {
    Merchant getMerchantDetails(String merchantId);

    List<Merchant> getAllMerchantDetails();

    boolean createMerchant(CreateMerchantRequest merchant);

    User getUserById(int id);

    User getUserByUsername(String username);

    boolean createReview(Review review);

    boolean deleteReview(int reviewId);

    List<Review> getAllReviewById(int merchantId);

    Review getReviewById(int reviewId);

    boolean createUser(UserCreationRequest userCreationRequest) throws NoSuchAlgorithmException, InvalidKeySpecException;

    void forgotPassword(String username);

    void supportRequest(int userId, String subject, String message);

    void confirmReset(UserConfirmPassword userConfirmPassword);

    User authenticate(UserAuthRequest userAuthRequest) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
