package com.service.dida.User;

import com.service.dida.User.DTO.SocialLoginReq;
import com.service.dida.Utils.ExceptionConfig.BaseException;
import com.service.dida.Utils.ExceptionConfig.BaseResponse;
import com.service.dida.Utils.ExceptionConfig.BaseResponseStatus;
import com.service.dida.Utils.Oauth.helper.SocialLoginType;
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
     * 소셜 로그인(KAKAO)
     * [POST] /{socialLoginType}/login
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

}
