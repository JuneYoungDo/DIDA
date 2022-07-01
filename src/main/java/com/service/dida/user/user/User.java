package com.service.dida.user.user;

import com.service.dida.card.Card;
import com.service.dida.market.Market;
import com.service.dida.user.wallet.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;

    private String nickname;
    private String description;
    private String profileUrl;

    private String payPwd;

    private String refreshToken;
    private String deviceToken;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Card> cards;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Market> markets;

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
