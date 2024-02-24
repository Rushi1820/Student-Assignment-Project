package com.task_assignment.Assignment.Controller;

import com.task_assignment.Assignment.Service.UserRoleService;
import com.task_assignment.Assignment.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/addRole")
    public void addRole(@RequestBody UserRole userRole) {
        userRoleService.addRole(userRole.getUserId(), userRole.getRole());
    }


}
