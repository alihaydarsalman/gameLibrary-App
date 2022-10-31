package com.turkcell.gamelibrary.system.business.abstracts;

import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetGameDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateGameRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateGameRequest;
import com.turkcell.gamelibrary.system.entities.sourceEntities.Game;

import java.util.List;

public interface GameService {

    DataResult<List<GetGameDto>> getAll();
    DataResult<GetGameDto> getById(int gameId);
    DataResult<List<GetGameDto>> getAllPaged(int pageNo, int pageSize);
    DataResult<List<GetGameDto>> getAllSorted(int pageNo, int pageSize, String sortBy, String sortDirection);
    DataResult<List<GetGameDto>> getAllByCampaign(int campaignId);
    Result createGame(CreateGameRequest createGameRequest);
    Result updateGame(UpdateGameRequest updateGameRequest);
    Result deleteGame(int gameId);

    //Diger servisler icin:
    Game getGameById(int gameId);
    Game getGameByGameName(String gameName);
    void isGameExistsById(int gameId);
}
