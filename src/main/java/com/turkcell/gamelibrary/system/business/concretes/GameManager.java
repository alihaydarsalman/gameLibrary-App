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
import com.turkcell.gamelibrary.system.dataAccess.GameDao;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetGameDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateGameRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateGameRequest;
import com.turkcell.gamelibrary.system.entities.sourceEntities.Campaign;
import com.turkcell.gamelibrary.system.entities.sourceEntities.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GameManager implements GameService {

    private final ModelMapperService modelMapperService;
    private final GameDao gameDao;
    private final CampaignService campaignService;

    @Autowired
    public GameManager(ModelMapperService modelMapperService, GameDao gameDao
                      ,CampaignService campaignService) {
        this.modelMapperService = modelMapperService;
        this.gameDao = gameDao;
        this.campaignService = campaignService;
    }

    @Override
    public DataResult<List<GetGameDto>> getAll() {

        isGameListEmpty();

        List<Game> games = this.gameDao.findAll();
        isDiscountExistsByEachGame(games);
        List<GetGameDto> response = games.stream()
                .map(game -> this.modelMapperService.forDto().map(game,GetGameDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetGameDto> getById(int gameId) {

        isGameExistsById(gameId);

        Game game = this.gameDao.findById(gameId);
        if (game.getCampaign() != null){
            game.setPrice(game.getPrice() - game.getCampaign().getDiscountAmount());
        }
        GetGameDto response = this.modelMapperService.forDto().map(game,GetGameDto.class);

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_GET);
    }

    @Override
    public DataResult<List<GetGameDto>> getAllByCampaign(int campaignId) {

        this.campaignService.isCampaignExistsById(campaignId);

        List<Game> games = this.gameDao.findAllByCampaign_Id(campaignId);
        isDiscountExistsByEachGame(games);
        List<GetGameDto> response = games.stream()
                .map(game -> this.modelMapperService.forDto().map(game,GetGameDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<List<GetGameDto>> getAllPaged(int pageNo, int pageSize) {

        isGameListEmpty();

        Pageable pageable = PageRequest.of(pageNo-1, pageSize);

        List<Game> games = gameDao.findAll(pageable).getContent();
        isDiscountExistsByEachGame(games);
        List<GetGameDto> response = games.stream()
                .map(game -> this.modelMapperService.forDto().map(game,GetGameDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_LIST_PAGED);
    }

    @Override
    public DataResult<List<GetGameDto>> getAllSorted(int pageNo, int pageSize, String sortBy, String sortDirection) {

        isGameListEmpty();

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);

        Page<Game> games = this.gameDao.findAll(pageable);
        List<Game> gameList = games.getContent();
        isDiscountExistsByEachGame(gameList);

        List<GetGameDto> response = gameList.stream()
                .map(game -> this.modelMapperService.forDto().map(game,GetGameDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_LIST_SORTED);
    }

    @Override
    public Result createGame(CreateGameRequest createGameRequest) {

        isGameExistsByName(createGameRequest.getGameName());

        Campaign campaign;
        if (createGameRequest.getCampaignId() != null){
            this.campaignService.isCampaignExistsById(createGameRequest.getCampaignId());
            campaign = this.campaignService.getCampaign(createGameRequest.getCampaignId());
        }else{
            campaign = null;
        }

        Game game = this.modelMapperService.forRequest().map(createGameRequest,Game.class);
        game.setId(0);
        game.setCampaign(campaign);
        this.gameDao.save(game);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_ADD);
    }

    @Override
    public Result updateGame(UpdateGameRequest updateGameRequest) {

        isGameExistsById(updateGameRequest.getId());
        isGameCreationDateAfterThanNow(updateGameRequest.getCreationDate());

        Campaign campaign;
        if (updateGameRequest.getCampaignId() != null){
            this.campaignService.isCampaignExistsById(updateGameRequest.getCampaignId());
            campaign = this.campaignService.getCampaign(updateGameRequest.getCampaignId());
        }else{
            campaign = null;
        }

        Game oldGame = this.gameDao.findByGameName(updateGameRequest.getGameName());

        if (this.gameDao.existsByGameName(updateGameRequest.getGameName()) && oldGame != null) {
            if (!(updateGameRequest.getId().equals(oldGame.getId()))){
                isGameExistsByName(updateGameRequest.getGameName());
            }
        }

        Game game = this.modelMapperService.forRequest().map(updateGameRequest,Game.class);
        game.setCampaign(campaign);
        this.gameDao.save(game);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteGame(int gameId) {

        isGameExistsById(gameId);

        this.gameDao.deleteById(gameId);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_DELETE);
    }


    @Override
    public Game getGameById(int gameId) {

        isGameExistsById(gameId);

        return this.gameDao.findById(gameId);
    }

    @Override
    public void isGameExistsById(int gameId){
        if (!this.gameDao.existsGameById(gameId)){
            throw new BusinessException(BusinessMessages.GameExceptionMessages.GAME_NOT_FOUND);
        }
    }

    @Override
    public Game getGameByGameName(String gameName) {

        if (!this.gameDao.existsByGameName(gameName)){
            throw new BusinessException(BusinessMessages.GameExceptionMessages.GAME_NOT_FOUND);
        }else{
            return this.gameDao.findByGameName(gameName);
        }
    }

    private void isGameListEmpty(){
        if (this.gameDao.findAll().isEmpty() || this.gameDao.findAll().size() <= 0){
            throw new BusinessException(BusinessMessages.GameExceptionMessages.GAME_NOT_FOUND);
        }
    }

    private void isGameExistsByName(String gameName){
        if (this.gameDao.existsByGameName(gameName)){
            throw new BusinessException(BusinessMessages.GameExceptionMessages.GAME_NAME_ALREADY_EXISTS);
        }
    }

    private void isGameCreationDateAfterThanNow(LocalDate localDate){
        if (localDate.isAfter(LocalDate.now())){
            throw new BusinessException(BusinessMessages.GameExceptionMessages.GAME_CREATION_DATE_ERROR);
        }
    }

    private void isDiscountExistsByEachGame(List<Game> games){
        for (Game game:games){
            if (game.getCampaign() != null){
                double gamePriceWithDiscount;
                gamePriceWithDiscount = game.getPrice() - game.getCampaign().getDiscountAmount();
                game.setPrice(gamePriceWithDiscount);
            }
        }
    }
}
