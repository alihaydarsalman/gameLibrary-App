package com.turkcell.gamelibrary.system.dataAccess;

import com.turkcell.gamelibrary.system.entities.sourceEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    User findUserByEmail(String email);
    User findUserByNickName(String nickName);
    Optional<User> findUserByNickNameOrEmail(String nickName, String email);
    Optional<User> findByNickNameOrEmail(String nickName, String email);
    User findById(int id);
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
}
