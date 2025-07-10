package employee.service;

import com.employee.dto.EmployeeDTO;
import com.employee.exception.ResourceNotFoundException;
import com.employee.mapper.EmployeeMapper;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private List<Employee> employeeList;
    private List<EmployeeDTO> employeeDTOList;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastNameFather("Doe")
                .lastNameMother("Smith")
                .position("Developer")
                .build();

        employeeDTO = EmployeeDTO.builder()
                .id(1L)
                .firstName("John")
                .lastNameFather("Doe")
                .lastNameMother("Smith")
                .position("Developer")
                .build();

        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Jane")
                .lastNameFather("Smith")
                .lastNameMother("Johnson")
                .position("Manager")
                .build();

        EmployeeDTO employeeDTO2 = EmployeeDTO.builder()
                .id(2L)
                .firstName("Jane")
                .lastNameFather("Smith")
                .lastNameMother("Johnson")
                .position("Manager")
                .build();

        employeeList = Arrays.asList(employee, employee2);
        employeeDTOList = Arrays.asList(employeeDTO, employeeDTO2);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployeeDTOs() {
        // Given
        when(employeeRepository.findAll()).thenReturn(employeeList);
        when(employeeMapper.toDTO(any(Employee.class)))
                .thenReturn(employeeDTO)
                .thenReturn(employeeDTOList.get(1));

        // When
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(employeeDTOList, result);
        verify(employeeRepository).findAll();
        verify(employeeMapper, times(2)).toDTO(any(Employee.class));
    }

    @Test
    void getEmployeeById_WhenEmployeeExists_ShouldReturnEmployeeDTO() {
        // Given
        when(employeeRepository.getReferenceById(eq(1L))).thenReturn(employee);
        when(employeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        // When
        EmployeeDTO result = employeeService.getEmployeeById(1L);

        // Then
        assertNotNull(result);
        assertEquals(employeeDTO, result);
        verify(employeeRepository).findById(1L);
        verify(employeeMapper).toDTO(employee);
    }

    @Test
    void getEmployeeById_WhenEmployeeDoesNotExist_ShouldThrowException() {
        // Given
        when(employeeRepository.findById(eq(1L))).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(1L);
        });
        verify(employeeRepository).findById(1L);
        verify(employeeMapper, never()).toDTO(any());
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployeeDTO() {
        // Given
        when(employeeMapper.toEntity(employeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        // When
        EmployeeDTO result = employeeService.createEmployee(employeeDTO);

        // Then
        assertNotNull(result);
        assertEquals(employeeDTO, result);
        verify(employeeMapper).toEntity(employeeDTO);
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toDTO(employee);
    }

    @Test
    void updateEmployee_WhenEmployeeExists_ShouldReturnUpdatedEmployeeDTO() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        // When
        EmployeeDTO result = employeeService.updateEmployee(1L, employeeDTO);

        // Then
        assertNotNull(result);
        assertEquals(employeeDTO, result);
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toDTO(employee);
        verify(employeeMapper).updateEntityFromDTO(employeeDTO, employee);
    }

    @Test
    void updateEmployee_WhenEmployeeDoesNotExist_ShouldThrowException() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, employeeDTO);
        });
        verify(employeeRepository).findById(1L);
        verify(employeeRepository, never()).save(any());
        verify(employeeMapper, never()).toDTO(any());
    }

    @Test
    void deleteEmployee_WhenEmployeeExists_ShouldDeleteSuccessfully() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // When
        employeeService.deleteEmployee(1L);

        // Then
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).delete(employee);
    }

    @Test
    void deleteEmployee_WhenEmployeeDoesNotExist_ShouldThrowException() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(1L);
        });
        verify(employeeRepository).findById(1L);
        verify(employeeRepository, never()).delete(any());
    }

    @Test
    void searchEmployeesByName_ShouldReturnMatchingEmployees() {
        // Given
        String searchName = "John";
        when(employeeRepository.findByFirstNameContainingIgnoreCase(searchName)).thenReturn(List.of(employee));
        when(employeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        // When
        List<EmployeeDTO> result = employeeService.searchEmployeesByName(searchName);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeDTO, result.get(0));
        verify(employeeRepository).findByFirstNameContainingIgnoreCase(searchName);
        verify(employeeMapper).toDTO(employee);
    }

    @Test
    void searchEmployeesByName_WhenNoMatches_ShouldReturnEmptyList() {
        // Given
        String searchName = "NonExistent";
        when(employeeRepository.findByFirstNameContainingIgnoreCase(searchName)).thenReturn(Collections.emptyList());

        // When
        List<EmployeeDTO> result = employeeService.searchEmployeesByName(searchName);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(employeeRepository).findByFirstNameContainingIgnoreCase(searchName);
        verify(employeeMapper, never()).toDTO(any());
    }
}
