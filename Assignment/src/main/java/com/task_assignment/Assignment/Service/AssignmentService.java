package com.task_assignment.Assignment.Service;


import com.task_assignment.Assignment.Enum.Role;
import com.task_assignment.Assignment.Exceptions.AssignmentNotFoundException;
import com.task_assignment.Assignment.Repository.AssignmentRepository;
import com.task_assignment.Assignment.Repository.UserRepository;
import com.task_assignment.Assignment.entity.Assignment;
import com.task_assignment.Assignment.entity.Course;
import com.task_assignment.Assignment.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@EnableCaching
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    private final CacheManager cacheManager;

    public AssignmentService( CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    @Cacheable(cacheNames = "assignments", key = "#studentUsername")
    public List<Assignment> getAllAssignments(String studentUsername) throws IllegalAccessException {
        User student = userRepository.findByUsername(studentUsername);

        if (student == null || student.getRole() != Role.STUDENT) {
            throw new IllegalAccessException("Invalid Student username provided or user is not a student.");
        }
        Course studentCourse = student.getCourse();

        List<Assignment> assignments = assignmentRepository.findByCourse(studentCourse);
        logger.info("Retrieved all users from the database and stored in cache");
        return assignments;
    }


    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }


    public Assignment createAssignment(Assignment assignment) throws IllegalAccessException {
        String teacherUsername = assignment.getTeacher().getUsername();
        User teacher = userRepository.findByUsername(teacherUsername);

        if (teacher != null && teacher.getRole() == Role.TEACHER) {
            Course teacherCourse = teacher.getCourse();
            if (teacherCourse != null && teacherCourse.equals(assignment.getCourse())) {
                assignment.setTeacher(teacher);
                assignment.setCreatedAt(LocalDateTime.now());
                return assignmentRepository.save(assignment);
            } else {
                throw new IllegalAccessException("Teacher can only assign tasks to their own course.");
            }
        } else {
            throw new IllegalAccessException("Invalid teacher username provided or user is not a teacher.");
        }
    }



    public Assignment updateAssignment(Long assignmentId, Assignment updatedAssignment) throws IllegalAccessException {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found"));

        String teacherUsername = existingAssignment.getTeacher().getUsername();
        User teacher = userRepository.findByUsername(teacherUsername);
        if (teacher == null || teacher.getRole() != Role.TEACHER) {
            throw new IllegalAccessException("Invalid teacher username provided or user is not a teacher.");
        }
        existingAssignment.setTitle(updatedAssignment.getTitle());
        existingAssignment.setDescription(updatedAssignment.getDescription());
        existingAssignment.setDueDate(updatedAssignment.getDueDate());
        existingAssignment = assignmentRepository.save(existingAssignment);

        evictAllAssignmentsFromCache();
        logger.info("Cache evicted and restored with updated assignments");
        return existingAssignment;
    }

    private void evictAllAssignmentsFromCache() {
        Cache cache = cacheManager.getCache("assignments");
        if (cache != null) {
            cache.clear();
        }
    }

    public void deleteAssignment(Long assignmentId, String teacherUsername) throws IllegalAccessException {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found"));
        User teacher = userRepository.findByUsername(teacherUsername);
        if (teacher == null || teacher.getRole() != Role.TEACHER) {
            throw new IllegalAccessException("Invalid teacher username provided or user is not a teacher.");
        }
        if (!Objects.equals(assignment.getTeacher().getUsername(), teacherUsername)) {
            throw new IllegalAccessException("You are not authorized to delete this assignment.");
        }
        assignmentRepository.deleteById(assignmentId);
    }

    
}
