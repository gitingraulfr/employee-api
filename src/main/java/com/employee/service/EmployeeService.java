package com.employee.service;

import com.employee.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long id);

    List<EmployeeDTO> searchEmployeesByName(String name);

    EmployeeDTO createEmployee(EmployeeDTO employeeCreateDTO);

    List<EmployeeDTO> createMultipleEmployees(List<EmployeeDTO> employees);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeUpdateDTO);

    void deleteEmployee(Long id);
}