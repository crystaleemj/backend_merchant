package com.crystal.merchant_backend.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.crystal.merchant_backend.entity.Review;

@Repository
public class ReviewRepo {
    
    @Autowired
    JdbcTemplate template;

    private final String GET_REVIEWS_BY_MERCHANTID = "SELECT * FROM reviews WHERE merchant_id=?";
    private final String GET_REVIEW_BY_ID = "SELECT * FROM reviews WHERE review_id=?";
    private final String GET_REVIEWS_BY_USERID = "SELECT * FROM reviews WHERE user_id=?";
    private final String CREATE_REVIEW_SQL = "INSERT into reviews(user_id, merchant_id, rating, feedback, image_url) VALUES (?,?,?,?,?)";
    private final String UPDATE_REVIEW_SQL = "UPDATE reviews SET rating = ?, feedback = ?, image_url = ? WHERE review_id = ?";
    private final String DELETE_REVIEW_SQL = "DELETE FROM reviews WHERE review_id = ?";

    // list all reviews of a particular merchant
    public List<Review> getReviewsByMerchantId(int merchant_id){
        return template.query(GET_REVIEWS_BY_MERCHANTID, BeanPropertyRowMapper.newInstance(Review.class), merchant_id);
    }

    // find review by id
    public Review getReviewById(int id){
        return template.queryForObject(GET_REVIEW_BY_ID, BeanPropertyRowMapper.newInstance(Review.class), id);
    }

    // find reviews by user 
    public List<Review> getReviewByUserId(int user_id){
        return template.query(GET_REVIEWS_BY_USERID, BeanPropertyRowMapper.newInstance(Review.class), user_id);
    }

    // create a new review 
    public Boolean createReview(Review review){
        Integer result = template.update(CREATE_REVIEW_SQL, review.getUserId(),review.getMerchantId(), review.getRating(), review.getFeedback(), review.getImageUrl());
        return result > 0 ? true : false;
    }

    // update an existing review
    public void updateReview(Review review) {
        template.update(UPDATE_REVIEW_SQL, review.getRating(), review.getFeedback(), review.getImageUrl(), review.getReviewId());
    }

    // Delete a review by ID
    public Boolean deleteReview(int id) {
        Integer result = template.update(DELETE_REVIEW_SQL, id);
        return result > 0 ? true : false;
    }
}
