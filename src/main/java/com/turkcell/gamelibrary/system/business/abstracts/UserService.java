package com.turkcell.gamelibrary.system.business.abstracts;

import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateUserRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.loginRequest.LoginRequestDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateUserRequest;
import com.turkcell.gamelibrary.system.entities.sourceEntities.User;
import org.springframework.http.ResponseEntity;


public interface UserService {

    Result signUp(CreateUserRequest createUserRequest);
    ResponseEntity<String> logIn(LoginRequestDto loginRequestDto);
    Result updateUser(UpdateUserRequest updateUserRequest);
    User getUserById(int userId);
    DataResult getById(int userId);
}
