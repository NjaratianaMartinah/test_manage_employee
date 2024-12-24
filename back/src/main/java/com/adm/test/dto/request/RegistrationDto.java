package com.adm.test.dto.request;

import com.adm.test.utility.validator.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationDto {
    @NotBlank
    @Email
    private String email;
    @ValidPassword(message = "Password format too weak.")
    private String password;
}
