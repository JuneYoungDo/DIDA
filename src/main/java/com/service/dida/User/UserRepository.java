package com.service.dida.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<Boolean> existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<Boolean> existsByNickname(String email);
}
