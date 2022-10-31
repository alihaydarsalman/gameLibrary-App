package com.turkcell.gamelibrary.system.business.concretes;

import com.turkcell.gamelibrary.system.business.abstracts.CampaignService;
import com.turkcell.gamelibrary.system.business.abstracts.GameService;
import com.turkcell.gamelibrary.system.business.constant.messages.BusinessMessages;
import com.turkcell.gamelibrary.system.core.exceptions.BusinessException;
import com.turkcell.gamelibrary.system.core.mapping.ModelMapperService;
import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.core.responseTypes.SuccessDataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.SuccessResult;
import com.turkcell.gamelibrary.system.dataAccess.CampaignDao;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetCampaignDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateCampaignRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateCampaignRequest;
import com.turkcell.gamelibrary.system.entities.sourceEntities.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignManager implements CampaignService {

    private final ModelMapperService modelMapperService;
    private final CampaignDao campaignDao;
    private final GameService gameService;

    @Autowired
    public CampaignManager(ModelMapperService modelMapperService, CampaignDao campaignDao
                          ,@Lazy GameService gameService) {
        this.modelMapperService = modelMapperService;
        this.campaignDao = campaignDao;
        this.gameService = gameService;
    }

    @Override
    public DataResult<List<GetCampaignDto>> getAll() {

        isCampaignListEmpty();

        List<Campaign> campaigns = this.campaignDao.findAll();
        List<GetCampaignDto> response = campaigns.stream()
                .map(campaign -> this.modelMapperService.forDto().map(campaign,GetCampaignDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetCampaignDto> getById(int campaignId) {

        isCampaignExistsById(campaignId);

        Campaign campaign = this.campaignDao.getById(campaignId);
        GetCampaignDto response = this.modelMapperService.forDto().map(campaign,GetCampaignDto.class);

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_GET);
    }

    @Override
    public Result createCampaign(CreateCampaignRequest createCampaignRequest) {

        isCampaignExistsByTitle(createCampaignRequest.getCampaignTitle());
        isCampaignStartDateBeforeNow(createCampaignRequest.getStartDate());
        isCampaignDatesValid(createCampaignRequest.getStartDate(),createCampaignRequest.getEndDate());

        Campaign campaign = this.modelMapperService.forRequest().map(createCampaignRequest,Campaign.class);
        campaign.setId(0);
        this.campaignDao.save(campaign);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_ADD);
    }

    @Override
    public Result updateCampaign(UpdateCampaignRequest updateCampaignRequest) {

        isCampaignExistsById(updateCampaignRequest.getId());
        isCampaignStartDateBeforeNow(updateCampaignRequest.getStartDate());
        isCampaignDatesValid(updateCampaignRequest.getStartDate(),updateCampaignRequest.getEndDate());

        //istekle gelen obje ayni objeyse bir şey olmasın
        //ama istekle gelen obje başka objeyse kontrole girsin
        Campaign oldCampaign = this.campaignDao.findCampaignByCampaignTitle(updateCampaignRequest.getCampaignTitle());

        if (this.campaignDao.existsCampaignByCampaignTitle(updateCampaignRequest.getCampaignTitle()) && oldCampaign != null) {
            if (!(updateCampaignRequest.getId().equals(oldCampaign.getId()))){
                isCampaignExistsByTitle(updateCampaignRequest.getCampaignTitle());
            }
        }

        Campaign campaign = this.modelMapperService.forRequest().map(updateCampaignRequest,Campaign.class);
        this.campaignDao.save(campaign);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteCampaign(int campaignId) {

        isCampaignExistsById(campaignId);

        this.campaignDao.deleteById(campaignId);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_DELETE);
    }

    @Override
    public void isCampaignExistsById(int campaignId){
        if (!this.campaignDao.existsCampaignById(campaignId)){
            throw new BusinessException(BusinessMessages.CampaignExceptionMessages.CAMPAIGN_NOT_FOUND);
        }
    }

    @Override
    public Campaign getCampaign(int campaignId) {
        return this.campaignDao.getById(campaignId);
    }

    private void isCampaignExistsByTitle(String campaignTitle){
        if (this.campaignDao.existsCampaignByCampaignTitle(campaignTitle)){
            throw new BusinessException(BusinessMessages.CampaignExceptionMessages.CAMPAIGN_TITLE_ALREADY_EXISTS);
        }
    }

    private void isCampaignListEmpty(){
        if (this.campaignDao.findAll().isEmpty() || this.campaignDao.findAll().size() <= 0){
            throw new BusinessException(BusinessMessages.CampaignExceptionMessages.CAMPAIGN_NOT_FOUND);
        }
    }

    private void isCampaignStartDateBeforeNow(LocalDate startDate){
        if (startDate.isBefore(LocalDate.now())){
            throw new BusinessException(BusinessMessages.CampaignExceptionMessages.CAMPAIGN_CREATION_DATE_ERROR);
        }
    }

    private void isCampaignDatesValid(LocalDate startDate, LocalDate endDate){
        if (startDate.isAfter(endDate)){
            throw new BusinessException(BusinessMessages.CampaignExceptionMessages.CAMPAIGN_CREATION_DATES_ERROR);
        }
    }
}
