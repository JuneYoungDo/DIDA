package com.service.dida.card;

import com.service.dida.exception.BaseException;
import com.service.dida.utils.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final JwtService jwtService;
    private final CardService cardService;

    /**
     * 내 보유 NFT 보기
     * [GET] /user/cards
     */
    @GetMapping("/user/cards")
    public ResponseEntity getMyPageCards() throws BaseException {
        Long userId = jwtService.getUserId();
        return new ResponseEntity(cardService.getMyCards(userId), HttpStatus.OK);
    }

}
