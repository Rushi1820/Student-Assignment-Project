package com.task_assignment.Assignment.Repository;

import com.task_assignment.Assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long Id);
    boolean existsByUsername(String username);
}