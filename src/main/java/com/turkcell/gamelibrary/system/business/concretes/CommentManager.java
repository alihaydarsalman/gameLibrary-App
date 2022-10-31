package com.turkcell.gamelibrary.system.business.concretes;

import com.turkcell.gamelibrary.system.business.abstracts.CommentService;
import com.turkcell.gamelibrary.system.business.abstracts.GameService;
import com.turkcell.gamelibrary.system.business.abstracts.UserService;
import com.turkcell.gamelibrary.system.business.constant.messages.BusinessMessages;
import com.turkcell.gamelibrary.system.core.exceptions.BusinessException;
import com.turkcell.gamelibrary.system.core.mapping.ModelMapperService;
import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.core.responseTypes.SuccessDataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.SuccessResult;
import com.turkcell.gamelibrary.system.dataAccess.CommentDao;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetCommentDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateCommentRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateCommentRequest;
import com.turkcell.gamelibrary.system.entities.sourceEntities.Comment;
import com.turkcell.gamelibrary.system.entities.sourceEntities.Game;
import com.turkcell.gamelibrary.system.entities.sourceEntities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentManager implements CommentService {

    private final ModelMapperService modelMapperService;
    private final CommentDao commentDao;
    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public CommentManager(ModelMapperService modelMapperService, CommentDao commentDao,
                          GameService gameService, UserService userService) {
        this.modelMapperService = modelMapperService;
        this.commentDao = commentDao;
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    public DataResult<List<GetCommentDto>> getAll() {

        isAllCommentListEmpty();

        List<Comment> comments = this.commentDao.findAll();
        List<GetCommentDto> response = comments.stream()
                .map(comment -> this.modelMapperService.forDto().map(comment,GetCommentDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.GeneralSuccessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<GetCommentDto> getById(int commentId) {

        isCommentExistsById(commentId);

        Comment comment = this.commentDao.findById(commentId);

        GetCommentDto response = this.modelMapperService.forDto().map(comment,GetCommentDto.class);

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_GET);
    }

    @Override
    public DataResult<List<GetCommentDto>> getByGameId(int gameId) {

        this.gameService.getGameById(gameId);
        isCommentListEmpty(gameId);

        List<Comment> comments = this.commentDao.findAllByGame_Id(gameId);
        List<GetCommentDto> response = comments.stream()
                .map(comment -> this.modelMapperService.forDto().map(comment,GetCommentDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_LIST);
    }

    @Override
    public DataResult<List<GetCommentDto>> getByGameName(String gameName) {

        this.gameService.getGameByGameName(gameName);
        isCommentExistsByGameName(gameName);

        List<Comment> comments = this.commentDao.findAllByGame_GameName(gameName);
        List<GetCommentDto> response = comments.stream()
                .map(comment -> this.modelMapperService.forDto().map(comment,GetCommentDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_LIST);
    }

    @Override
    public Result submitComment(CreateCommentRequest createCommentRequest) {

        Game game = this.gameService.getGameById(createCommentRequest.getGameId());
        User user = this.userService.getUserById(createCommentRequest.getUserId());

        Comment comment = this.modelMapperService.forRequest().map(createCommentRequest,Comment.class);
        comment.setId(0);
        comment.setGame(game);
        comment.setCommentUpdateDate(null);
        comment.setUser(user);
        this.commentDao.save(comment);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_ADD);
    }

    @Override
    public Result updateComment(UpdateCommentRequest updateCommentRequest) {

        isCommentExistsById(updateCommentRequest.getCommentId());
        User user = this.userService.getUserById(updateCommentRequest.getUserId());

        Comment comment = this.commentDao.findById(updateCommentRequest.getCommentId());

        comment.setCommentBody(updateCommentRequest.getCommentBody());
        comment.setCommentUpdateDate(LocalDate.now());
        comment.setUser(user);

        this.commentDao.save(comment);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteComment(int commentId) {

        isCommentExistsById(commentId);

        this.commentDao.deleteById(commentId);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_DELETE);
    }

    private void isCommentExistsById(int comId){
        if(!this.commentDao.existsById(comId)){
            throw new BusinessException(BusinessMessages.CommentExceptionMessages.COMMENT_NOT_FOUND);
        }
    }

    private void isCommentExistsByGameName(String gameName){
        if (!this.commentDao.existsCommentByGame_GameName(gameName)){
            throw new BusinessException(BusinessMessages.CommentExceptionMessages.COMMENT_LIST_EMPTY_MESSAGE);
        }
    }

    private void isCommentListEmpty(int gameId){
        if (!this.commentDao.existsCommentByGame_Id(gameId)){
            throw new BusinessException(BusinessMessages.CommentExceptionMessages.COMMENT_LIST_EMPTY_MESSAGE);
        }
    }

    private void isAllCommentListEmpty(){
        if (this.commentDao.findAll().isEmpty() || this.commentDao.findAll().size() <= 0){
            throw new BusinessException(BusinessMessages.CommentExceptionMessages.COMMENT_LIST_EMPTY_MESSAGE);
        }
    }
}
