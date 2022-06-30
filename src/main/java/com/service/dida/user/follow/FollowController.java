package com.service.dida.user.follow;

import com.service.dida.exception.BaseException;
import com.service.dida.user.follow.dto.ClickUserFollowReq;
import com.service.dida.utils.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final JwtService jwtService;

    /**
     * 사용자 팔로우 하기(팔로우 취소하기)
     * [POST] /user/follow
     */
    @PostMapping("/user/follow")
    public ResponseEntity userFollow(@RequestBody ClickUserFollowReq clickUserFollowReq) throws BaseException {
        Long userId = jwtService.getUserId();
        followService.clickUserFollow(userId, clickUserFollowReq.getUserId());
        return new ResponseEntity(200, HttpStatus.OK);
    }

}
