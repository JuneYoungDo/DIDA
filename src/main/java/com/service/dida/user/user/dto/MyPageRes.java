package com.service.dida.user.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageRes {
    private Long userId;
    private String nickname;
    private String profileUrl;
    private String description;
    private boolean getWallet;
    private int cardCnt;
    private Long followerCnt;
    private Long followingCnt;
}
