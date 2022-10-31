package com.turkcell.gamelibrary.system.dataAccess;

import com.turkcell.gamelibrary.system.entities.sourceEntities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameDao extends JpaRepository<Game,Integer> {

    Game findById(int gameId);
    Game findByGameName(String gameName);
    List<Game> findAllByCampaign_Id(int campaignId);
    boolean existsByGameName(String gameName);
    boolean existsGameById(int gameId);
}
