package com.task_assignment.Assignment.Repository;

import com.task_assignment.Assignment.Enum.Role;
import com.task_assignment.Assignment.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    boolean existsByUserIdAndRole(Long userId, Role role);
}
