package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "Operations related to Employee entity")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Get all employees", description = "Retrieves a list of all employees")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeDTO.class)))
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(summary = "Get an employee by ID", description = "Retrieves an employee by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(
            @Parameter(description = "ID of the employee to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(summary = "Create multiple employees", description = "Creates a list of new employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the employees",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<List<EmployeeDTO>> createEmployees(
            @Parameter(description = "List of employees to create")
            @Valid @RequestBody List<EmployeeDTO> dtos) {
        return ResponseEntity.ok(employeeService.createMultipleEmployees(dtos));
    }

    @Operation(summary = "Update an employee", description = "Updates an existing employee by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the employee",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @Parameter(description = "ID of the employee to update") @PathVariable Long id,
            @Parameter(description = "Updated employee information")
            @Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @Operation(summary = "Delete an employee", description = "Deletes an employee by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the employee"),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "ID of the employee to delete") @PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search employees by name", description = "Retrieves a list of employees whose names match the search criteria")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the matching employees",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeDTO.class)))
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> searchByName(
            @Parameter(description = "Name to search for") @RequestParam String name) {
        return ResponseEntity.ok(employeeService.searchEmployeesByName(name));
    }
}
