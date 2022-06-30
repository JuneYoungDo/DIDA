package com.service.dida.user.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;
    private Long followingId;   // 팔로우를 하는 사람
    private Long followerId;    // 팔로우를 당한 사람
    private boolean mode;

    public void changeMode(boolean mode) {
        this.mode = mode;
    }
}
