package com.turkcell.gamelibrary.system.business.abstracts;

import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetCampaignDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateCampaignRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateCampaignRequest;
import com.turkcell.gamelibrary.system.entities.sourceEntities.Campaign;

import java.util.List;

public interface CampaignService {

    DataResult<List<GetCampaignDto>> getAll();
    DataResult<GetCampaignDto> getById(int campaignId);
    Result createCampaign(CreateCampaignRequest createCampaignRequest);
    Result updateCampaign(UpdateCampaignRequest updateCampaignRequest);
    Result deleteCampaign(int campaignId);

    //Diger servisler icin:
    void isCampaignExistsById(int campaignId);
    Campaign getCampaign(int campaignId);
}
