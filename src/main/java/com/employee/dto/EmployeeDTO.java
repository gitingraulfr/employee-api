package com.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastNameFather;
    private String lastNameMother;
    private Integer age;
    private String gender;
    private LocalDate birthDate;
    private String position;
    private LocalDateTime createdAt;
    private boolean active;
}
