package com.task_assignment.Assignment.Controller;

import com.task_assignment.Assignment.Service.UserService;
import com.task_assignment.Assignment.entity.User;
import com.task_assignment.Assignment.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("adduser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createduser=userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createduser);
    }
    @PutMapping("/update/{userID}")
    public ResponseEntity<User> updateUser(@PathVariable Long userID ,@RequestBody User user) {
        User updatedUser = userService.updateUser(userID, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
