package com.task_assignment.Assignment.Repository;

import com.task_assignment.Assignment.entity.Assignment;
import com.task_assignment.Assignment.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>{

    List<Assignment> findByCourse(Course studentCourse);
}
