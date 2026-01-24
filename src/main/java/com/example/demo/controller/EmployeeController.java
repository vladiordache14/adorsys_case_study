package com.example.demo.controller;


import com.example.demo.api.EmployeeApi;
import com.example.demo.model.EmployeeRequest;
import com.example.demo.model.EmployeeResponse;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;
    @Override
    public ResponseEntity<EmployeeResponse> createEmployee(EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeRequest));
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(String employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
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
