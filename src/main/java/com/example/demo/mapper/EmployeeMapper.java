package com.example.demo.mapper;

import com.example.demo.model.EmployeeEntity;
import com.example.demo.model.EmployeeRequest;
import com.example.demo.model.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeEntity toEmployeeEntity(EmployeeRequest employeeRequest);

    EmployeeResponse toEmployeeResponse(EmployeeEntity employeeEntity);

    void updateEntityFromRequest(EmployeeRequest request, @MappingTarget EmployeeEntity entity);
}
