package com.turkcell.gamelibrary.system.api.controller;

import com.turkcell.gamelibrary.system.business.abstracts.CommentService;
import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetCommentDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateCommentRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    private final CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getAll")
    DataResult<List<GetCommentDto>> getAll(){
        return this.commentService.getAll();
    }

    @GetMapping("/getByComment")
    DataResult<GetCommentDto> getById(@RequestParam int commentId){
        return this.commentService.getById(commentId);
    }

    @GetMapping("/getByGame")
    DataResult<List<GetCommentDto>> getByGameId(@RequestParam int gameId){
        return this.commentService.getByGameId(gameId);
    }

    @GetMapping("/getByGameName")
    DataResult<List<GetCommentDto>> getByGameName(@RequestParam String gameName){
        return this.commentService.getByGameName(gameName);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/submit")
    Result submitComment(@RequestBody @Valid CreateCommentRequest createCommentRequest){
        return this.commentService.submitComment(createCommentRequest);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/update")
    Result updateComment(@RequestBody @Valid UpdateCommentRequest updateCommentRequest){
        return this.commentService.updateComment(updateCommentRequest);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @DeleteMapping("/delete")
    Result deleteComment(@RequestParam int commentId){
        return this.commentService.deleteComment(commentId);
    }
}
