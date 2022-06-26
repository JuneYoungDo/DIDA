package com.service.dida.user.user;

import com.service.dida.user.user.dto.CheckNicknameReq;
import com.service.dida.user.user.dto.CreateUserReq;
import com.service.dida.user.user.dto.SocialLoginReq;
import com.service.dida.utils.service.JwtService;
import com.service.dida.exception.BaseException;
import com.service.dida.exception.BaseResponse;
import com.service.dida.exception.BaseResponseStatus;
import com.service.dida.utils.oauth.helper.SocialLoginType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 소셜 로그인(KAKAO/APPLE)
     * [POST] /{socialLoginType}/login
     */
    @PostMapping("/{socialLoginType}/login")
    public ResponseEntity socialLogin(@RequestBody SocialLoginReq socialLoginReq,
                                      @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) throws BaseException {
        try {
            return new ResponseEntity(userService.socialLogin(socialLoginType, socialLoginReq.getIdToken()),
                    HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(new BaseResponse(BaseResponseStatus.INVALID_PATH),
                    HttpStatus.valueOf(400));
        }
    }

    /**
     * 닉네임 중복 확인
     * [POST] /user/nickname
     */
    @PostMapping("/user/nickname")
    public ResponseEntity checkNickname(@RequestBody CheckNicknameReq checkNicknameReq) {
        return new ResponseEntity(userService.checkNickname(checkNicknameReq.getNickname()), HttpStatus.OK);
    }

    /**
     * 소셜 회원가입
     * [POST] /new/user
     */
    @PostMapping("/new/user")
    public ResponseEntity createUser(@RequestBody CreateUserReq createUserReq) throws BaseException {
        return new ResponseEntity(userService.createUser2(createUserReq), HttpStatus.OK);
    }

    /**
     * refreshToken을 이용한 accessToken 재발급
     * [GET] /login/refresh
     */
    @GetMapping("/login/refresh")
    public ResponseEntity renewalAccessToken() throws BaseException {
        String refreshToken = jwtService.getRefreshJwt();
        Long userId = jwtService.getUserIdFromRefreshToken();
        return new ResponseEntity(userService.renewalAccessToken(userId, refreshToken), HttpStatus.OK);
    }

    /**
     * 내 프로필 확인 (보유 NFT 제외)
     * [GET] /user
     */
    @GetMapping("/user")
    public ResponseEntity getMyPage() throws BaseException {
        Long userId = jwtService.getUserId();
        return new ResponseEntity(userService.getMyPageProfile(userId), HttpStatus.OK);
    }
}
