package com.turkcell.gamelibrary.system.api.controller;

import com.turkcell.gamelibrary.system.business.abstracts.GameService;
import com.turkcell.gamelibrary.system.business.constant.messages.AppConstants;
import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetGameDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateGameRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateGameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GamesController {

    private final GameService gameService;

    @Autowired
    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/getAll")
    DataResult<List<GetGameDto>> getAll(){
        return this.gameService.getAll();
    }

    @GetMapping("/getById")
    DataResult<GetGameDto> getById(@RequestParam int gameId){
        return this.gameService.getById(gameId);
    }

    @GetMapping("/getGamesByCampaign")
    DataResult<List<GetGameDto>> getAllByCampaign(@RequestParam int campaignId){
        return this.gameService.getAllByCampaign(campaignId);
    }

    @GetMapping("/getAllPaged")
    DataResult<List<GetGameDto>> getAllPaged(@RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                             @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize){
        return this.gameService.getAllPaged(pageNo,pageSize);
    }

    @GetMapping("/getAllSorted")
    DataResult<List<GetGameDto>> getAllSorted(@RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                              @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                              @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                              @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection){
        return this.gameService.getAllSorted(pageNo,pageSize,sortBy,sortDirection);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    Result createGame(@RequestBody @Valid CreateGameRequest createGameRequest){
        return this.gameService.createGame(createGameRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    Result updateGame(@RequestBody @Valid UpdateGameRequest updateGameRequest){
        return this.gameService.updateGame(updateGameRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    Result deleteGame(@RequestParam int gameId){
        return this.gameService.deleteGame(gameId);
    }
}
