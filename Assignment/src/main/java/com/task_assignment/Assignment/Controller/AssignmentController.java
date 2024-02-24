package com.task_assignment.Assignment.Controller;

import com.task_assignment.Assignment.Service.AssignmentService;
import com.task_assignment.Assignment.entity.Assignment;
import com.task_assignment.Assignment.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;
    @GetMapping("/getallassignments")
    public ResponseEntity<List<Assignment>> getAllAssignments(@RequestParam String studentUsername) throws IllegalAccessException {
        List<Assignment> assignments = assignmentService.getAllAssignments(studentUsername);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentService.getAssignmentById(id);
        return assignment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/createAssignment")
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) throws IllegalAccessException {
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
    }

    @PutMapping("UpdateAssignmentById/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment updatedAssignment) {
        try {
            Assignment updated = assignmentService.updateAssignment(id, updatedAssignment);
            return ResponseEntity.ok(updated);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("deleteAssignmentById/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        try {
            String teacherUsername = requestBody.get("teacherUsername");
            assignmentService.deleteAssignment(id, teacherUsername);
            return ResponseEntity.ok("Assignment with ID " + id + " deleted successfully.");
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access: " + e.getMessage());
        }
    }

}