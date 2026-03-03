package com.example.demo.controller;


import com.example.demo.api.EmployeeApi;
import com.example.demo.model.EmployeeRequest;
import com.example.demo.model.EmployeeResponse;
import com.example.demo.service.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.example.authaspect.annotation.Authorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Authorize
@RestController
@AllArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    @Authorize(roles={"ADMIN"})
    @Override
    public ResponseEntity<EmployeeResponse> createEmployee(EmployeeRequest employeeRequest) {
        var savedEmployee = employeeService.createEmployee(employeeRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEmployee.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedEmployee);
    }

    @Authorize(roles={"ADMIN"})
    @Override
    public ResponseEntity<Void> deleteEmployee(String employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @Override
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @Override
    public ResponseEntity<EmployeeResponse> updateEmployee(String employeeId, EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employeeRequest));
    }
}
