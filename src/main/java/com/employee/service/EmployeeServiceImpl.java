package com.employee.service;

import com.employee.dto.EmployeeDTO;
import com.employee.exception.ResourceNotFoundException;
import com.employee.mapper.EmployeeMapper;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return employeeMapper.toDTO(employee);
    }

    @Override
    public List<EmployeeDTO> searchEmployeesByName(String name) {
        return employeeRepository.findAll()
                .stream()
                .filter(e -> (e.getFirstName() + " " + e.getLastNameFather()).toLowerCase().contains(name.toLowerCase()))
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Employee employee = employeeMapper.toEntity(dto);
        employee.setCreatedAt(LocalDateTime.now());
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDTO> createMultipleEmployees(List<EmployeeDTO> employees) {
        List<Employee> employeeList = employees.stream()
                .map(employeeMapper::toEntity)
                .peek(e -> e.setCreatedAt(LocalDateTime.now()))
                .collect(Collectors.toList());
        return employeeRepository.saveAll(employeeList)
                .stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        employeeMapper.updateEntityFromDTO(dto, employee);
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        employeeRepository.delete(employee);
    }
}