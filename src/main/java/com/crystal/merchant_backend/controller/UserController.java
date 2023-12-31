package com.crystal.merchant_backend.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crystal.merchant_backend.dto.UserAuthRequest;
import com.crystal.merchant_backend.dto.UserConfirmPassword;
import com.crystal.merchant_backend.dto.UserCreationRequest;
import com.crystal.merchant_backend.dto.UserDetailRequest;
import com.crystal.merchant_backend.dto.UserDetailUsernameRequest;
import com.crystal.merchant_backend.dto.UserSupport;
import com.crystal.merchant_backend.entity.User;
import com.crystal.merchant_backend.service.MainService;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin()
public class UserController {

    @Autowired
    MainService mainService;

    @PostMapping("/find/id")
    public ResponseEntity<?> getUserDetailsById(@RequestBody UserDetailRequest userDetailRequest) {
        if(userDetailRequest.getUserId() != 0){
            return new ResponseEntity<User>(mainService.getUserById(userDetailRequest.getUserId()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("{\"msg\":\"User ID cannot be empty\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> getUserDetailsByUsername(@RequestBody UserAuthRequest userAuthRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(userAuthRequest.getUsername() != "" && userAuthRequest.getUsername() != null && userAuthRequest.getPassword() != "" && userAuthRequest.getPassword() != null){
            return new ResponseEntity<User>(mainService.authenticate(userAuthRequest), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("{\"msg\":\"User data cannot be empty\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/create")
    public ResponseEntity<String> createNewUser(@RequestBody UserCreationRequest userCreationRequest) throws NoSuchAlgorithmException, InvalidKeySpecException{
        if(userCreationRequest != null){
            if(mainService.createUser(userCreationRequest)){
                return new ResponseEntity<String>("{\"msg\":\"Successful\"}", HttpStatus.OK);
            };
        }
        return new ResponseEntity<String>("{\"msg\":\"User details cannot be empty\"}", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetByUsername(@RequestBody UserDetailUsernameRequest userDSDetailUsernameRequest) {
        if(userDSDetailUsernameRequest.getUsername() != "" && userDSDetailUsernameRequest.getUsername() != null){
            mainService.forgotPassword(userDSDetailUsernameRequest.getUsername());
            return new ResponseEntity<String>("{\"msg\":\"Successful\"}", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("{\"msg\":\"Username cannot be empty\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/support")
    public ResponseEntity<?> supportUser(@RequestBody UserSupport userSupport) {
        if(userSupport.getUserId() != 0){
            mainService.supportRequest(userSupport.getUserId(), userSupport.getSubject(), userSupport.getMessage());
            return new ResponseEntity<String>("{\"msg\":\"Successful\"}", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("{\"msg\":\"Username cannot be empty\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset/confirm")
    public ResponseEntity<?> confirmResetByUsername(@RequestBody UserConfirmPassword userConfirmPassword) {
        if(userConfirmPassword.getPassword() != "" && userConfirmPassword.getPassword() != null){
            mainService.confirmReset(userConfirmPassword);
            return new ResponseEntity<String>("{\"msg\":\"Successful\"}", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("{\"msg\":\"Username cannot be empty\"}", HttpStatus.BAD_REQUEST);
        }
    }
}
