package com.example.demo.service;

import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.model.EmployeeEntity;
import com.example.demo.model.EmployeeRequest;
import com.example.demo.model.EmployeeResponse;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private UUID employeeId;
    private EmployeeEntity employeeEntity;
    private EmployeeResponse employeeResponse;
    private EmployeeRequest employeeRequest;

    @BeforeEach
    void setUp() {
        employeeId = UUID.randomUUID();
        employeeEntity = new EmployeeEntity();
        employeeResponse = new EmployeeResponse();
        employeeRequest = new EmployeeRequest();
    }

    @Test
    void getEmployeeById_Success() {
        // when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeEntity));
        when(employeeMapper.toEmployeeResponse(employeeEntity)).thenReturn(employeeResponse);

        // then
        EmployeeResponse result = employeeService.getEmployeeById(employeeId.toString());

        assertNotNull(result);
        verify(employeeRepository).findById(employeeId);
    }

    @Test
    void getEmployeeById_NotFound() {
        // given
        when(employeeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () ->
                employeeService.getEmployeeById(employeeId.toString())
        );
    }

    @Test
    void createEmployee_Success() {
        // given
        when(employeeMapper.toEmployeeEntity(employeeRequest)).thenReturn(employeeEntity);
        when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
        when(employeeMapper.toEmployeeResponse(employeeEntity)).thenReturn(employeeResponse);

        // when
        EmployeeResponse result = employeeService.createEmployee(employeeRequest);

        // then
        assertNotNull(result);
        verify(employeeRepository).save(any(EmployeeEntity.class));
    }

    @Test
    void deleteEmployee_Success() {
        // when
        employeeService.deleteEmployee(employeeId.toString());

        // then
        verify(employeeRepository).deleteById(employeeId);
    }
}