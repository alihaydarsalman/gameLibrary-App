package com.turkcell.gamelibrary.system.dataAccess;

import com.turkcell.gamelibrary.system.entities.sourceEntities.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignDao extends JpaRepository<Campaign,Integer> {

    Campaign findCampaignByCampaignTitle(String campaignTitle);
    boolean existsCampaignById(int campaignId);
    boolean existsCampaignByCampaignTitle(String campaignTitle);
    Campaign getById(int campaignId);
}
