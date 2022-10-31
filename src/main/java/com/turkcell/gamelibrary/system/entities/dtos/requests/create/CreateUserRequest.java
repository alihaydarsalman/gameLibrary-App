package com.turkcell.gamelibrary.system.entities.dtos.requests.create;

import com.turkcell.gamelibrary.system.business.constant.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{2,20}",
            message = ValidationMessages.UserValidationMessages.NAME_VALIDATION)
    private String firstName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{2,20}",
            message = ValidationMessages.UserValidationMessages.LAST_NAME_VALIDATION)
    private String lastName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ-_* 0-9]{2,20}",
            message = ValidationMessages.UserValidationMessages.NICK_NAME_VALIDATION)
    private String nickName;

    @NotNull
    @NotBlank
    @Email(message = ValidationMessages.UserValidationMessages.EMAIL_VALIDATION)
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>.]).{8,20}$",
            message = ValidationMessages.UserValidationMessages.PASSWORD_VALIDATION_ERROR)
    private String password;
}
