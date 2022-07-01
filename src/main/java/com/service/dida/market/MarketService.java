package com.service.dida.market;

import com.service.dida.card.Card;
import com.service.dida.card.CardRepository;
import com.service.dida.exception.BaseException;
import com.service.dida.exception.BaseResponseStatus;
import com.service.dida.market.dto.UploadMarketReq;
import com.service.dida.user.user.User;
import com.service.dida.user.user.UserRepository;
import com.service.dida.utils.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final UtilService utilService;
    private final MarketRepository marketRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Transactional
    public void save(Market market) {
        marketRepository.save(market);
    }

    public void createMarket(User user, Card card, double price) {
        Market market = Market.builder()
                .price(price)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .user(user)
                .card(card)
                .build();
        save(market);
    }

    public void uploadMarket(Long userId, UploadMarketReq uploadMarketReq) throws BaseException {
        User user = userRepository.getByUserId(userId).orElse(null);
        boolean checkPwd = utilService.checkPayPwd(user, uploadMarketReq.getPayPwd());
        if (checkPwd == false) {
            throw new BaseException(BaseResponseStatus.NEED_WALLET_OR_WRONG_PWD);
        }
        // 수수료 전달 필요

        Card card = cardRepository.getByCardId(uploadMarketReq.getCardId()).orElse(null);
        if (card == null || card.isDeleted() || card.getUser() != user) {
            throw new BaseException(BaseResponseStatus.INVALID_CARD_ID);
        }

        List<Market> markets = card.getMarkets();
        if (markets != null && markets.get(markets.size() - 1).isDeleted() == false) {
            throw new BaseException(BaseResponseStatus.ALREADY_IN_MARKET);
        } else {
            createMarket(user, card, uploadMarketReq.getPrice());
        }
    }


}
