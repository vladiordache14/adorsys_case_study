package com.example.demo.service;

import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.model.EmployeeEntity;
import com.example.demo.model.EmployeeRequest;
import com.example.demo.model.EmployeeResponse;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        return employeeMapper.toEmployeeResponse(employeeRepository.save(employeeMapper.toEmployeeEntity(employeeRequest)));
    }

    @Override
    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(UUID.fromString(employeeId));
    }

    @Override
    public EmployeeResponse getEmployeeById(String employeeId) {
        return employeeMapper.toEmployeeResponse(employeeRepository.findById(UUID.fromString(employeeId)).orElseThrow());
    }

    @Override
    public List<EmployeeResponse> getEmployees() {
        return employeeRepository.findAll().stream().map(employeeMapper::toEmployeeResponse).toList();
    }

    @Override
    public EmployeeResponse updateEmployee(String employeeId, EmployeeRequest employeeRequest) {
        EmployeeEntity employeeEntity = employeeRepository.findById(UUID.fromString(employeeId)).orElseThrow();
        employeeMapper.updateEntityFromRequest(employeeRequest, employeeEntity);
        return employeeMapper.toEmployeeResponse(employeeRepository.save(employeeEntity));
    }
}
