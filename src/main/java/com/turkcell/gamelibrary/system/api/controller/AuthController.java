package com.turkcell.gamelibrary.system.api.controller;

import com.turkcell.gamelibrary.system.business.abstracts.UserService;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateUserRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.loginRequest.LoginRequestDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signUp")
    Result signUp(@RequestBody @Valid CreateUserRequest createUserRequest){
        return this.userService.signUp(createUserRequest);
    }

    @PostMapping("/logIn")
    ResponseEntity<String> logIn(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return this.userService.logIn(loginRequestDto);
    }

    @PostMapping("/signIn")
    ResponseEntity<String> authenticateUser(@RequestBody @Valid LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsernameOremail(),loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Giriş yapıldı", HttpStatus.OK);
    }

    @PutMapping("/updateUserFields")
    Result updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest){
        return this.userService.updateUser(updateUserRequest);
    }
}
