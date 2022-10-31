package com.turkcell.gamelibrary.system.entities.dtos.requests.loginRequest;

import com.turkcell.gamelibrary.system.business.constant.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotNull
    @NotBlank
    private String usernameOremail;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>.]).{8,20}$",
            message = ValidationMessages.UserValidationMessages.PASSWORD_VALIDATION_ERROR)
    private String password;
}
