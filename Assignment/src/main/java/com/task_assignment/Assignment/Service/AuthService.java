package com.task_assignment.Assignment.Service;

import com.task_assignment.Assignment.Repository.UserRepository;
import com.task_assignment.Assignment.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Date;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public String login(String username, String password) {
        // For demo purposes, validate username and password
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {

            return generateToken(username);
        }
        return null;
    }

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);

    public String generateToken(String username) {
        Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY)
                .compact();
    }
}
