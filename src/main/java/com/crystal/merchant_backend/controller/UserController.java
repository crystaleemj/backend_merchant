package com.crystal.merchant_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crystal.merchant_backend.dto.UserConfirmPassword;
import com.crystal.merchant_backend.dto.UserCreationRequest;
import com.crystal.merchant_backend.dto.UserDetailRequest;
import com.crystal.merchant_backend.dto.UserDetailUsernameRequest;
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

    @PostMapping("/find/username")
    public ResponseEntity<?> getUserDetailsByUsername(@RequestBody UserDetailUsernameRequest userDSDetailUsernameRequest) {
        if(userDSDetailUsernameRequest.getUsername() != "" && userDSDetailUsernameRequest.getUsername() != null){
            return new ResponseEntity<User>(mainService.getUserByUsername(userDSDetailUsernameRequest.getUsername()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("{\"msg\":\"Username cannot be empty\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/create")
    public ResponseEntity<String> createNewUser(@RequestBody UserCreationRequest userCreationRequest){
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
