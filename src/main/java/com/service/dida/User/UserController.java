package com.service.dida.User;

import com.service.dida.User.DTO.CheckNicknameReq;
import com.service.dida.User.DTO.CreateUserReq;
import com.service.dida.User.DTO.SocialLoginReq;
import com.service.dida.utils.ExceptionConfig.BaseException;
import com.service.dida.utils.ExceptionConfig.BaseResponse;
import com.service.dida.utils.ExceptionConfig.BaseResponseStatus;
import com.service.dida.utils.Oauth.helper.SocialLoginType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    /**
     * 소셜 로그인(KAKAO/APPLE)
     * [POST] /{socialLoginType}/login
     *
     * @param socialLoginType (KAKAO, APPLE)
     */
    @PostMapping("/{socialLoginType}/login")
    public ResponseEntity socialLogin(@RequestBody SocialLoginReq socialLoginReq,
                                      @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        try {
            return new ResponseEntity(userService.socialLogin(socialLoginType, socialLoginReq.getIdToken()),
                    HttpStatus.valueOf(200));
        } catch (BaseException exception) {
            return new ResponseEntity(new BaseResponse(exception.getStatus()),
                    HttpStatus.valueOf(exception.getStatus().getStatus()));
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
    public ResponseEntity createUser(@RequestBody CheckNicknameReq checkNicknameReq) {
        return new ResponseEntity(userService.checkNickname(checkNicknameReq.getNickname()), HttpStatus.valueOf(200));
    }

    /**
     * 소셜 회원가입
     * [POST] /new/user
     */
    @PostMapping("/new/user")
    public ResponseEntity createUser(@RequestBody CreateUserReq createUserReq) {
        try {
            return new ResponseEntity(userService.createUser2(createUserReq),HttpStatus.valueOf(200));
        } catch (BaseException exception) {
            return new ResponseEntity(new BaseResponse(exception.getStatus()),
                    HttpStatus.valueOf(exception.getStatus().getStatus()));
        }
    }
}
