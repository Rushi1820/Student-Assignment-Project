package com.task_assignment.Assignment.Service;

import com.task_assignment.Assignment.Enum.Role;
import com.task_assignment.Assignment.Repository.UserRoleRepository;
import com.task_assignment.Assignment.entity.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public void addRole(Long userId, Role role) {
        // Check for existing role to avoid duplicates
        if (!userRoleRepository.existsByUserIdAndRole(userId, role)) {
            UserRole userRole = new UserRole(userId, role);
            userRoleRepository.save(userRole);
        } else {
            // Handle the case where the role already exists
            throw new IllegalStateException("Role already exists for user");
        }
    }
}
