package com.service.dida.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCardsRes {
    private Long cardId;
    private String userName;
    private String cardName;
    private String imgUrl;
    private String price;
}
