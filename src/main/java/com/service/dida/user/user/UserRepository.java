package com.service.dida.user.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByUserId(Long userId);

    Optional<Boolean> existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<Boolean> existsByNickname(String email);
}
