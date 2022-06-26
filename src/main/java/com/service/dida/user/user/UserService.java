package com.service.dida.user.user;

import com.service.dida.user.follow.FollowRepository;
import com.service.dida.user.user.dto.CheckNicknameRes;
import com.service.dida.user.user.dto.CreateUserReq;
import com.service.dida.user.user.dto.LoginRes;
import com.service.dida.user.user.dto.MyPageRes;
import com.service.dida.utils.service.JwtService;
import com.service.dida.exception.BaseException;
import com.service.dida.exception.BaseResponseStatus;
import com.service.dida.utils.oauth.helper.SocialLoginType;
import com.service.dida.utils.oauth.service.AppleService;
import com.service.dida.utils.oauth.service.KakaoService;
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
    private final FollowRepository followRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public void createUser(String email) {
        User user = User.builder()
                .email(email)
                .nickname("")
                .description("")
                .profileUrl("")
                .payPwd("")
                .refreshToken("")
                .deviceToken("")
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .editedAt(LocalDateTime.now())
                .build();
        save(user);
    }

    @Transactional
    public LoginRes socialLogin(SocialLoginType socialLoginType, String idToken) throws BaseException, IOException {
        if (idToken.equals("") || idToken == null) {
            if (socialLoginType.equals(SocialLoginType.KAKAO)) {
                throw new BaseException(BaseResponseStatus.INVALID_KAKAO_ID_TOKEN);
            } else if (socialLoginType.equals(SocialLoginType.APPLE)) {
                throw new BaseException(BaseResponseStatus.INVALID_APPLE_ID_TOKEN);
            }
        }
        String email = "";
        if (socialLoginType.equals(SocialLoginType.KAKAO)) {
            email = kakaoService.getKakaoUserInfo(idToken);
        } else if (socialLoginType.equals(SocialLoginType.APPLE)) {
            email = appleService.userEmailFromApple(idToken);
        }

        Boolean flag = userRepository.existsByEmail(email).orElse(false);
        if (flag == true) { // 로그인
            User user = userRepository.findByEmail(email).orElse(null);
            user.changeRefreshToken(jwtService.createRefreshToken(user.getUserId()));
            return new LoginRes(jwtService.createJwt(user.getUserId()), user.getRefreshToken());
        } else {    // 이메일 반환
            return new LoginRes(email, "");
        }
    }

    public CheckNicknameRes checkNickname(String nickname) {
        return new CheckNicknameRes(userRepository.existsByNickname(nickname).orElse(false));
    }

    @Transactional
    public LoginRes createUser2(CreateUserReq createUserReq) throws BaseException {
        if (userRepository.existsByEmail(createUserReq.getEmail()).orElse(false) == true)
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL);
        if (userRepository.existsByNickname(createUserReq.getNickname()).orElse(false) == true)
            throw new BaseException(BaseResponseStatus.INVALID_NICKNAME);
        createUser(createUserReq.getEmail());
        User user = userRepository.findByEmail(createUserReq.getEmail()).orElse(null);
        user.changeNickname(createUserReq.getNickname());
        user.changeRefreshToken(jwtService.createRefreshToken(user.getUserId()));
        return new LoginRes(jwtService.createJwt(user.getUserId()), user.getRefreshToken());
    }

    public LoginRes renewalAccessToken(Long userId, String refreshToken) throws BaseException {
        if (refreshToken.equals("") || refreshToken.length() == 0)
            throw new BaseException(BaseResponseStatus.EMPTY_REFRESH_TOKEN);
        if (!jwtService.verifyRefreshToken(refreshToken)) {
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        } else {
            User user = userRepository.getByUserId(userId).orElse(null);
            if (refreshToken.equals(user.getRefreshToken())) {
                return new LoginRes(jwtService.createJwt(userId), refreshToken);
            } else {
                throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
            }
        }
    }

    public MyPageRes getMyPageProfile(Long userId) {
        User user = userRepository.getByUserId(userId).orElse(null);
        boolean getWallet;
        if (user.getWallet() == null) {
            getWallet = false;
        } else {
            getWallet = true;
        }
        return new MyPageRes(user.getUserId(), user.getNickname(), user.getProfileUrl(), user.getDescription(), getWallet,
                user.getCards().size(),
                followRepository.getFollowerCnt(userId).orElse(0L),
                followRepository.getFollowingCnt(userId).orElse(0L));
    }
}
