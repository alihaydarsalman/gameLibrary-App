package com.turkcell.gamelibrary.system.entities.dtos.getDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGameDto {

    private Integer id;
    private String gameName;
    private String explanation;
    private String manufacturer;
    private Double price;
    private GetCampaignDto campaign;
}
