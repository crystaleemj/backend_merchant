package com.crystal.merchant_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crystal.merchant_backend.entity.Review;
import com.crystal.merchant_backend.service.MainService;

@RestController
@RequestMapping("/v1/review")
public class ReviewController {
    @Autowired
    MainService mainService;

    @PutMapping(value="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createReview(@RequestPart Review reviewDetails, @RequestPart MultipartFile image) {
        // Insert into S3 and retrieve URL of file
        String amazonFile = "honto";
        reviewDetails.setImageUrl(amazonFile);

        if (mainService.createReview(reviewDetails)) {
            return new ResponseEntity<String>("{\"msg\":\"Successful\"}", HttpStatus.OK);
        }
        return new ResponseEntity<String>("{\"msg\":\"Error Creating\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/merchant/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByMerchant(@PathVariable("id") int merchantId) {
        return new ResponseEntity<List<Review>>(mainService.getAllReviewById(merchantId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") int reviewId) {
        if (mainService.deleteReview(reviewId)) {
            return new ResponseEntity<String>("{\"msg\":\"Successful\"}", HttpStatus.OK);
        }
        return new ResponseEntity<String>("{\"msg\":\"Error Deleting\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") int reviewId) {
        return new ResponseEntity<Review>(mainService.getReviewById(reviewId), HttpStatus.OK);
    }
}
