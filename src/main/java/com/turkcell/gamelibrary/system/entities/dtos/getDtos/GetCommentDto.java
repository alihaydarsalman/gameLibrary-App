package com.turkcell.gamelibrary.system.entities.dtos.getDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentDto {

    private int id;
    private String nickName;
    private String commentBody;
    private LocalDate commentDate;
    private LocalDate commentUpdateDate;
    private int gameId;
}
