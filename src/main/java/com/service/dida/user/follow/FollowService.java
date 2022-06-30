package com.service.dida.user.follow;

import com.service.dida.exception.BaseException;
import com.service.dida.exception.BaseResponseStatus;
import com.service.dida.user.user.User;
import com.service.dida.user.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(Follow follow) {
        followRepository.save(follow);
    }

    public void createFollow(Long userId, Long otherId) {
        Follow follow = Follow.builder()
                .followingId(userId)
                .followerId(otherId)
                .mode(true)
                .build();
        save(follow);
    }

    @Transactional
    public void changeButton(Follow follow) {
        if (follow.isMode() == true) {
            follow.changeMode(false);
        } else {
            follow.changeMode(true);
        }
    }

    @Transactional
    public void clickUserFollow(Long myUserId, Long otherId) throws BaseException {
        User other = userRepository.getByUserId(otherId).orElse(null);
        if (other == null || other.isDeleted()) {
            throw new BaseException(BaseResponseStatus.INVALID_USER);
        }
        Follow follow = followRepository.getFollowStatus(myUserId, otherId).orElse(null);
        if (follow == null) {
            createFollow(myUserId, otherId);
        } else {
            changeButton(follow);
        }
    }

    public boolean isFollowUser(Long myId, Long otherId) {
        Follow follow = followRepository.getFollowStatus(myId,otherId).orElse(null);
        if(follow == null || follow.isMode() == false) {
            return false;
        } else {
            return true;
        }
    }

}
