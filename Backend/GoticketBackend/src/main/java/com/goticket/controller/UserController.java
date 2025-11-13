package com.goticket.controller;

import com.goticket.model.User;
import com.goticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 🆕 NEW: Get current logged-in user details by email
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestParam String email) {
        try {
            List<User> allUsers = userService.getAllUsers();
            User user = allUsers.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
            
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unable to fetch user details");
        }
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}