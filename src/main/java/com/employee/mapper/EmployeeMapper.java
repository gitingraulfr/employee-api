package com.employee.mapper;

import com.employee.dto.EmployeeDTO;
import com.employee.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDTO toDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .middleName(employee.getMiddleName())
                .lastNameFather(employee.getLastNameFather())
                .lastNameMother(employee.getLastNameMother())
                .age(employee.getAge())
                .gender(employee.getGender())
                .birthDate(employee.getBirthDate())
                .position(employee.getPosition())
                .createdAt(employee.getCreatedAt())
                .active(employee.isActive())
                .build();
    }

    public Employee toEntity(EmployeeDTO dto) {
        return Employee.builder()
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastNameFather(dto.getLastNameFather())
                .lastNameMother(dto.getLastNameMother())
                .age(dto.getAge())
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .position(dto.getPosition())
                .active(dto.isActive())
                .build();
    }

    public void updateEntityFromDTO(EmployeeDTO dto, Employee employee) {
        if (dto.getFirstName() != null) employee.setFirstName(dto.getFirstName());
        if (dto.getMiddleName() != null) employee.setMiddleName(dto.getMiddleName());
        if (dto.getLastNameFather() != null) employee.setLastNameFather(dto.getLastNameFather());
        if (dto.getLastNameMother() != null) employee.setLastNameMother(dto.getLastNameMother());
        if (dto.getAge() != null) employee.setAge(dto.getAge());
        if (dto.getGender() != null) employee.setGender(dto.getGender());
        if (dto.getBirthDate() != null) employee.setBirthDate(dto.getBirthDate());
        if (dto.getPosition() != null) employee.setPosition(dto.getPosition());
        employee.setActive(dto.isActive());
    }
}
