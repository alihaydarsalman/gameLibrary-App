package com.turkcell.gamelibrary.system.entities.dtos.getDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCampaignDto {

    private Integer id;
    private String campaignTitle;
    private String campaignDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double discountAmount;
}
