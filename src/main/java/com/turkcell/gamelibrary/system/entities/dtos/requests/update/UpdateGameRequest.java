package com.turkcell.gamelibrary.system.entities.dtos.requests.update;

import com.turkcell.gamelibrary.system.business.constant.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGameRequest {

    @NotNull
    @Min(value = 1, message = ValidationMessages.GeneralValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private Integer id;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqxyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQXYZ:/!? 0-9]{3,50}",
             message = ValidationMessages.GameValidationMessages.GAME_NAME_VALIDATION_ERROR)
    private String gameName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqxyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQXYZ.!?',_:=;*-/ 0-9]{5,200}",
             message = ValidationMessages.GameValidationMessages.GAME_EXPLANATION_VALIDATION_ERROR)
    private String explanation;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ 0-9]{2,50}",
             message = ValidationMessages.GameValidationMessages.GAME_MANUFACTURER_VALIDATION_ERROR)
    private String manufacturer;

    @NotNull
    @Min(value = 0, message = ValidationMessages.GameValidationMessages.GAME_PRICE_VALIDATION_ERROR)
    private Double price;

    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate creationDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    @Nullable
    private Integer campaignId;
}
