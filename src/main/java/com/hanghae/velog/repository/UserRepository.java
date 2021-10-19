package com.hanghae.velog.repository;

import com.hanghae.velog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserName(String userName);

    Optional<User> findByuserIdAndPassword(String userId, String password);
}
