package com.crystal.merchant_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crystal.merchant_backend.service.MainService;

@RestController
@RequestMapping("/v1")
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("{'msg':'Running'}");
    }
}
