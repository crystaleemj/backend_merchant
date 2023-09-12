package com.crystal.merchant_backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.crystal.merchant_backend.dto.UserCreationRequest;
import com.crystal.merchant_backend.entity.User;

@Repository 
public class UserRepo {
    
    @Autowired
    JdbcTemplate template;

    private final String FIND_USER_BY_ID = "SELECT * FROM users WHERE user_id=?";
    private final String FIND_USER_BY_USERNAME = "SELECT * FROM users WHERE username=?";
    private final String COUNT_USERNAME = "SELECT COUNT(username) FROM users WHERE username=?";
    private final String CREATE_USER_SQL = "INSERT INTO users(username, password, email) VALUES (?,?,?)";
    private final String UPDATE_USER_SQL = "UPDATE users SET password = ?, email = ? WHERE user_id = ?";

    // find user by id
    public User findUserById(int id){
        return template.queryForObject(FIND_USER_BY_ID, BeanPropertyRowMapper.newInstance(User.class), id);
    }

    // find user by username
    public User findUserByUsername(String username){
        return template.queryForObject(FIND_USER_BY_USERNAME, BeanPropertyRowMapper.newInstance(User.class), username);
    }

    // create new user
    public Boolean createUser(UserCreationRequest user){
        Integer result = template.update(CREATE_USER_SQL, user.getUsername(), user.getPassword(), user.getEmail());
        return result > 0 ? true : false;
    }

    public Boolean usernameAvailable(String username){
        Integer rowCount = template.queryForObject(COUNT_USERNAME,Integer.class, username);
        return rowCount>0 ? false : true;
    }

    // update user information
    public void updateReview(User user) {
        template.update(UPDATE_USER_SQL, user.getPassword(), user.getEmail());
    }
}
