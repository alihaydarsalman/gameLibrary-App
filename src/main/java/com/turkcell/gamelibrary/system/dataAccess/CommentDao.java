package com.turkcell.gamelibrary.system.dataAccess;

import com.turkcell.gamelibrary.system.entities.sourceEntities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment,Integer> {

    Comment findById(int commentId);
    List<Comment> findAllByGame_Id(int gameId);
    List<Comment> findAllByGame_GameName(String gameName);
    boolean existsCommentByGame_GameName(String gameName);
    boolean existsCommentByGame_Id(int gameId);
}
