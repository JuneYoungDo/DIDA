package com.service.dida.card;

import com.service.dida.card.dto.GetCardsRes;
import com.service.dida.user.user.User;
import com.service.dida.user.user.UserRepository;
import com.service.dida.utils.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UtilService utilService;

    public List<GetCardsRes> getMyCards(Long userId) {
        User user = userRepository.getByUserId(userId).orElse(null);
        List<Card> cards = user.getCards();
        List<GetCardsRes> myCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            String price = "";
            if (card.isMarketed()) {
                price = utilService.doubleToString(card.getMarkets().get(card.getMarkets().size() - 1).getPrice());
            }
            myCards.add(new GetCardsRes(card.getCardId(), user.getNickname(), card.getTitle(), card.getImgUrl(), price));
        }
        return myCards;
    }


}
