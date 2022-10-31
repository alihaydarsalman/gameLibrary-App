package com.turkcell.gamelibrary.system.entities.dtos.requests.create;

import com.turkcell.gamelibrary.system.business.constant.messages.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {

    @NotNull(message = ValidationMessages.CommentValidationMessages.COMMENT_NOT_SELECTED_GAME)
    @Min(value = 1, message = ValidationMessages.GeneralValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int gameId;

    @NotNull
    @Min(value = 1, message = ValidationMessages.GeneralValidationMessages.ID_CANNOT_LESS_THEN_ONE)
    private int userId;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqxyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQXYZ.!?,:;'_=*-/ 0-9]{1,1000}",
            message = ValidationMessages.CommentValidationMessages.COMMENT_BODY_VALIDATION)
    private String commentBody;
}
