package com.service.dida.card;

import com.service.dida.market.Market;
import com.service.dida.user.user.User;
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
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
    private String type;
    private String id;
    private String title;
    private String description;
    private String txHash;
    private String imgUrl;

    private boolean marketed;

    private boolean deleted;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Market> markets;
}
