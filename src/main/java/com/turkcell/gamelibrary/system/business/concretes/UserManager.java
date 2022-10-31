package com.turkcell.gamelibrary.system.business.concretes;

import com.turkcell.gamelibrary.system.business.abstracts.GameService;
import com.turkcell.gamelibrary.system.business.abstracts.UserService;
import com.turkcell.gamelibrary.system.business.constant.messages.BusinessMessages;
import com.turkcell.gamelibrary.system.core.exceptions.BusinessException;
import com.turkcell.gamelibrary.system.core.mapping.ModelMapperService;
import com.turkcell.gamelibrary.system.core.responseTypes.DataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.Result;
import com.turkcell.gamelibrary.system.core.responseTypes.SuccessDataResult;
import com.turkcell.gamelibrary.system.core.responseTypes.SuccessResult;
import com.turkcell.gamelibrary.system.dataAccess.RoleDao;
import com.turkcell.gamelibrary.system.dataAccess.UserDao;
import com.turkcell.gamelibrary.system.entities.dtos.getDtos.GetUserDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.create.CreateUserRequest;
import com.turkcell.gamelibrary.system.entities.dtos.requests.loginRequest.LoginRequestDto;
import com.turkcell.gamelibrary.system.entities.dtos.requests.update.UpdateUserRequest;
import com.turkcell.gamelibrary.system.entities.sourceEntities.Role;
import com.turkcell.gamelibrary.system.entities.sourceEntities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;

@Service
public class UserManager implements UserService {

    private final ModelMapperService modelMapperService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final RoleDao roleDao;


    public UserManager(@Lazy ModelMapperService modelMapperService, @Lazy AuthenticationManager authenticationManager,
                       @Lazy PasswordEncoder passwordEncoder
                       ,UserDao userDao, RoleDao roleDao) {
        this.modelMapperService = modelMapperService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public Result signUp(CreateUserRequest createUserRequest) {

        isUserExistsByEmail(createUserRequest.getEmail());
        isUserExistsByNickName(createUserRequest.getNickName());

        User user = new User();
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastname(createUserRequest.getLastName());
        user.setNickName(createUserRequest.getNickName());
        user.setRegisterDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setEmail(createUserRequest.getEmail());

        Role role = this.roleDao.findByRoleName("ROLE_USER").get();
        user.setRoles(Collections.singleton(role));

        this.userDao.save(user);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_REGISTER);
    }


    @Override
    public ResponseEntity<String> logIn(LoginRequestDto loginRequestDto) {

        if (!(this.userDao.existsByNickName(loginRequestDto.getUsernameOremail()))){
            if(!(this.userDao.existsByEmail(loginRequestDto.getUsernameOremail()))){
                return new ResponseEntity<>(BusinessMessages.UserExceptionMessages.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
        }

        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsernameOremail()
                        ,loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(BusinessMessages.GeneralSuccessMessages.SUCCESS_LOG_IN,HttpStatus.OK);
    }


    @PostMapping("/signIn")
    ResponseEntity<String> authenticateUser(@RequestBody @Valid LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsernameOremail(),loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Giriş yapıldı", HttpStatus.OK);
    }

    @Override
    public Result updateUser(UpdateUserRequest updateUserRequest) {

        isUserExistById(updateUserRequest.getId());

        User oldUser2 = this.userDao.findUserByNickName(updateUserRequest.getNickName());
        if (oldUser2 != null && this.userDao.existsByNickName(updateUserRequest.getNickName())){
            if (updateUserRequest.getId() != oldUser2.getId()){
                isUserExistsByNickName(updateUserRequest.getNickName());
            }
        }

        User oldUser = this.userDao.findUserByEmail(updateUserRequest.getEmail());
        if (oldUser != null && this.userDao.existsByEmail(updateUserRequest.getEmail())){
            if (updateUserRequest.getId() != oldUser.getId()){
                isUserExistsByEmail(updateUserRequest.getEmail());
            }
        }

        User user = this.userDao.findById(updateUserRequest.getId());
        user.setId(updateUserRequest.getId());
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastname(updateUserRequest.getLastName());
        user.setNickName(updateUserRequest.getNickName());
        user.setEmail(updateUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));

        this.userDao.save(user);

        return new SuccessResult(BusinessMessages.GeneralSuccessMessages.SUCCESS_UPDATE);
    }

    @Override
    public DataResult getById(int userId) {

        isUserExistById(userId);

        User user = this.userDao.findById(userId);
        GetUserDto response = this.modelMapperService.forDto().map(user,GetUserDto.class);

        return new SuccessDataResult(response,BusinessMessages.GeneralSuccessMessages.SUCCESS_GET);
    }

    @Override
    public User getUserById(int userId){
        isUserExistById(userId);

        return this.userDao.findById(userId);
    }

    private void isUserExistById(int id){
        if (!userDao.existsById(id)){
            throw new BusinessException(BusinessMessages.UserExceptionMessages.USER_NOT_FOUND);
        }
    }

    private void isUserExistsByEmail(String email){
        if (userDao.existsByEmail(email)){
            throw new BusinessException(BusinessMessages.UserExceptionMessages.EMAIL_ALREADY_EXISTS);
        }
    }

    private void isUserExistsByNickName(String nickName){
        if (userDao.existsByNickName(nickName)){
            throw new BusinessException(BusinessMessages.UserExceptionMessages.USERNAME_EXISTS);
        }
    }
}
