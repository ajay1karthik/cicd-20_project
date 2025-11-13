package com.goticket.service;

import com.goticket.model.User;
import com.goticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        // Set default approval to false for new users (except ADMIN)
        if (user.isApproved() == false && !"ADMIN".equals(user.getRole())) {
            user.setApproved(false);
        }
        // Auto-approve ADMIN users
        if ("ADMIN".equals(user.getRole())) {
            user.setApproved(true);
        }
        return userRepository.save(user);
    }

    @Override
    public boolean validateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Check both password AND approval status
            return user.getPassword().equals(password) && user.isApproved();
        }
        return false;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(updatedUser.getPassword());
            }
            // Allow updating approval status
            user.setApproved(updatedUser.isApproved());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User promoteToManager(Long id) {
        return userRepository.findById(id).map(user -> {
            user.setRole("MANAGER");
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}