package com.task_assignment.Assignment.Service;

import com.task_assignment.Assignment.Config.SecurityConfig;
import com.task_assignment.Assignment.Enum.Role;
import com.task_assignment.Assignment.Repository.CourseRepository;
import com.task_assignment.Assignment.Repository.UserRepository;
import com.task_assignment.Assignment.entity.Course;
import com.task_assignment.Assignment.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;


    public User addUser(User user) {

        return userRepository.save(user);
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long userID ,User user) {
        Optional<User> existingUserOptional = userRepository.findById(userID);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setCourse(user.getCourse());
            existingUser.setRole(user.getRole());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User not found with id: " + user.getId());
        }
    }

}


