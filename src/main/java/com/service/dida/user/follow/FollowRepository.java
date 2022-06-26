package com.service.dida.user.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query(value = "select COUNT(F) from Follow F where F.followerId = :userId and F.mode = true")
    Optional<Long> getFollowerCnt(Long userId);

    @Query(value = "select COUNT(F) from Follow F where F.followingId = :userId and F.mode = true")
    Optional<Long> getFollowingCnt(Long userId);
}
