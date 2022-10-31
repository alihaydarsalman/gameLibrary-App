package com.turkcell.gamelibrary.system.entities.dtos.requests.create;

import com.turkcell.gamelibrary.system.business.constant.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCampaignRequest {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqxyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQXYZ:/!?%.,' 0-9]{3,50}",
            message = ValidationMessages.CampaignValidationMessages.CAMPAIGN_TITLE_VALIDATION_ERROR)
    private String campaignTitle;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqxyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQXYZ:/!?'=%., 0-9]{5,200}",
            message = ValidationMessages.CampaignValidationMessages.CAMPAIGN_DESCRIPTION_VALIDATION_ERROR)
    private String campaignDescription;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Min(value = 0, message = ValidationMessages.CampaignValidationMessages.CAMPAIGN_DISCOUNT_AMOUNT_VALIDATION_ERROR)
    private Double discountAmount;
}
