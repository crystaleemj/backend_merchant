package com.crystal.merchant_backend.repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.crystal.merchant_backend.dto.UserAuthRequest;
import com.crystal.merchant_backend.dto.UserConfirmPassword;
import com.crystal.merchant_backend.dto.UserCreationRequest;
import com.crystal.merchant_backend.entity.User;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRepo {

    @Autowired
    JdbcTemplate template;

    @Autowired
    private JavaMailSender javaMailSender;

    private final String FIND_USER_BY_ID = "SELECT * FROM users WHERE user_id=?";
    private final String FIND_USER_BY_USERNAME = "SELECT * FROM users WHERE username=?";
    private final String COUNT_USERNAME = "SELECT COUNT(username) FROM users WHERE username=?";
    private final String CREATE_USER_SQL = "INSERT INTO users(username, password, email, reset) VALUES (?,?,?,0)";
    private final String UPDATE_USER_SQL = "UPDATE users SET password = ?, reset=1 WHERE user_id = ?";
    private final String CONFIRM_USER_SQL = "UPDATE users SET password = ?, reset=0 WHERE user_id = ?";

    // find user by id
    public User findUserById(int id) {
        return template.queryForObject(FIND_USER_BY_ID, BeanPropertyRowMapper.newInstance(User.class), id);
    }

    // find user by username
    public User findUserByUsername(String username) {
        return template.queryForObject(FIND_USER_BY_USERNAME, BeanPropertyRowMapper.newInstance(User.class), username);
    }

    // create new user
    public Boolean createUser(UserCreationRequest user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String base64Hash = hashWithSHA512(user.getPassword());
        Integer result = template.update(CREATE_USER_SQL, user.getUsername(), base64Hash, user.getEmail());
        return result > 0 ? true : false;
    }

    public Boolean usernameAvailable(String username) {
        Integer rowCount = template.queryForObject(COUNT_USERNAME, Integer.class, username);
        return rowCount > 0 ? false : true;
    }

    // update user information
    public Integer updatePassword(String userPassword, int userId) {
        return template.update(UPDATE_USER_SQL, userPassword, userId);
    }

    public void forgotPassword(String username) {
        User userData = findUserByUsername(username);
        try {
            String pass = generatePass(5);
            String generatedPass = hashWithSHA512(pass);
            Integer result = updatePassword(generatedPass, userData.getUser_id());
            if (result > 0) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom("hyperx66@outlook.com");
                mailMessage.setTo(userData.getEmail());
                mailMessage.setText("Here is your recovery password:\n" + pass);
                mailMessage.setSubject("Recovery password for: " + username);

                // Sending the mail
                javaMailSender.send(mailMessage);
                log.info("Recovery password sent to " + userData.getEmail());
            } else {
                log.error("Error updating DB");
            }
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            log.error("Error sending email");
            e.printStackTrace();
        }
    }

    public void supportRequest(int userId, String subject, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("donotreply.merchantrecovery@gmail.com");
            mailMessage.setTo("donotreply.merchantrecovery@gmail.com");
            mailMessage.setText("A support request has been raised\n\nUser ID:" + userId + "\nMessage:" + message);
            mailMessage.setSubject("Support Ticket: " + subject);

            // Sending the mail
            javaMailSender.send(mailMessage);
            log.info("Support ticket for " + userId);
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            log.error("Error sending email");
            e.printStackTrace();
        }
    }

    public void confirmPassword(UserConfirmPassword userConfirmPassword) {
        String base64Hash = hashWithSHA512(userConfirmPassword.getPassword());
        template.update(CONFIRM_USER_SQL, base64Hash, userConfirmPassword.getUserId());
    }

    public User authenticate(UserAuthRequest userAuthRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = template.queryForObject(FIND_USER_BY_USERNAME, BeanPropertyRowMapper.newInstance(User.class),
                userAuthRequest.getUsername());

        String base64Hash = hashWithSHA512(userAuthRequest.getPassword());
        if (user.getPassword().equals(base64Hash)) {
            return user;
        } else {
            return null;
        }
    }

    private String hashWithSHA512(String input) {
        StringBuilder result = new StringBuilder();
        try {
          MessageDigest md = MessageDigest.getInstance("SHA-512");
          md.update("salt".getBytes());
    
          byte [] digested = md.digest(input.getBytes());
          for (int i = 0; i < digested.length; i++) {
            result.append(Integer.toHexString(0xFF & digested[i]));
          }
        } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException("Bad algorithm");
        }
        return result.toString();
      }

    private String generatePass(int length) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
