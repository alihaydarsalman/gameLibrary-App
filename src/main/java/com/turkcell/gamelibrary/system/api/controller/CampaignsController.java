package com.turkcell.gamelibrary.system.api.controller;

import com.turkcell.gamelibrary.system.business.abstracts.CampaignService;
import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetCampaignDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateCampaignRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateCampaignRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignsController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignsController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/getAll")
    DataResult<List<GetCampaignDto>> getAll(){
        return this.campaignService.getAll();
    }

    @GetMapping("/getById")
    DataResult<GetCampaignDto> getById(@RequestParam int campaignId){
        return this.campaignService.getById(campaignId);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    Result createCampaign(@RequestBody @Valid CreateCampaignRequest createCampaignRequest){
        return this.campaignService.createCampaign(createCampaignRequest);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    Result updateCampaign(@RequestBody @Valid UpdateCampaignRequest updateCampaignRequest){
        return this.campaignService.updateCampaign(updateCampaignRequest);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    Result deleteCampaign(@RequestParam int campaignId){
        return this.campaignService.deleteCampaign(campaignId);
    }
}
