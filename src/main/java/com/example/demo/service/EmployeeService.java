package com.example.demo.service;


import com.example.demo.model.EmployeeRequest;
import com.example.demo.model.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest employeeRequest) ;

    void deleteEmployee(String employeeId);

    EmployeeResponse getEmployeeById(String employeeId);

    List<EmployeeResponse> getEmployees();

    EmployeeResponse updateEmployee(String employeeId, EmployeeRequest employeeRequest);
}
