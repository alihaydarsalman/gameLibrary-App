package com.turkcell.gamelibrary.system.business.abstracts;

import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetCommentDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateCommentRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateCommentRequest;

import java.util.List;

public interface CommentService {

    DataResult<List<GetCommentDto>> getAll();
    DataResult<GetCommentDto> getById(int commentId);
    DataResult<List<GetCommentDto>> getByGameId(int gameId);
    DataResult<List<GetCommentDto>> getByGameName(String gameName);
    Result submitComment(CreateCommentRequest createCommentRequest);
    Result updateComment(UpdateCommentRequest updateCommentRequest);
    Result deleteComment(int commentId);
}
