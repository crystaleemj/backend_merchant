package com.crystal.merchant_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crystal.merchant_backend.entity.Merchant;
import com.crystal.merchant_backend.service.MainService;

@RestController
@RequestMapping("/v1")
@CrossOrigin
public class MerchantController {

    @Autowired
    MainService mainService;

    @GetMapping("/merchant/{id}")
    public ResponseEntity<?> getMerchantDetails(@PathVariable("id") String merchantId) {
        if(merchantId != null && merchantId != ""){
            return new ResponseEntity<Merchant>(mainService.getMerchantDetails(merchantId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("{\"msg\":\"Merchant ID cannot be empty\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/merchant")
    public ResponseEntity<List<Merchant>> getAllMerchantDetails() {
        return ResponseEntity.ok(mainService.getAllMerchantDetails());
    }
}
