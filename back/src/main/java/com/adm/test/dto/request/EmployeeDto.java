package com.adm.test.dto.request;

import com.adm.test.utility.validator.BirthDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class EmployeeDto {
    @NotBlank
    @Length(min = 2, max = 200)
    private String fullName;

    @NotNull
    @BirthDate
    private LocalDate dateOfBirth;
}
