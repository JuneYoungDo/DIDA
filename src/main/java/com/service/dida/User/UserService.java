package com.service.dida.User;

import com.service.dida.User.DTO.LoginRes;
import com.service.dida.Utils.ExceptionConfig.BaseException;
import com.service.dida.Utils.ExceptionConfig.BaseResponseStatus;
import com.service.dida.Utils.JwtService;
import com.service.dida.Utils.Oauth.helper.SocialLoginType;
import com.service.dida.Utils.Oauth.service.AppleService;
import com.service.dida.Utils.Oauth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KakaoService kakaoService;
    private final AppleService appleService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public void createUser(String email) {
        User user = User.builder()
                .email(email)
                .refreshToken("")
                .deviceToken("")
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .build();
        save(user);
    }

    @Transactional
    public LoginRes socialLogin(SocialLoginType socialLoginType, String idToken) throws BaseException, IOException {
        if (idToken.equals("") || idToken == null) {
            if(socialLoginType.equals(SocialLoginType.KAKAO))
                throw new BaseException(BaseResponseStatus.INVALID_KAKAO_ID_TOKEN);
            else if(socialLoginType.equals(SocialLoginType.APPLE))
                throw new BaseException(BaseResponseStatus.INVALID_APPLE_ID_TOKEN);
        }
        String email = "";
        if (socialLoginType.equals(SocialLoginType.KAKAO))
            email = kakaoService.getKakaoUserInfo(idToken);
        else if (socialLoginType.equals(SocialLoginType.APPLE))
            email = appleService.userEmailFromApple(idToken);

        Boolean flag = userRepository.existsByEmail(email).orElse(false);
        if (flag == true) { // 로그인
            User user = userRepository.findByEmail(email).orElse(null);
            user.setRefreshToken(jwtService.createRefreshToken(user.getUserId()));
            return new LoginRes(jwtService.createJwt(user.getUserId()), user.getRefreshToken());
        } else {    // 회원 가입
            createUser(email);
            User user = userRepository.findByEmail(email).orElse(null);
            user.setRefreshToken(jwtService.createRefreshToken(user.getUserId()));
            return new LoginRes(jwtService.createJwt(user.getUserId()), user.getRefreshToken());
        }
    }
}
