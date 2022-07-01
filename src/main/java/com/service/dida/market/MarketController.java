package com.service.dida.market;

import com.service.dida.exception.BaseException;
import com.service.dida.market.dto.UploadMarketReq;
import com.service.dida.utils.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;
    private final JwtService jwtService;

    /**
     * 마켓에 NFT 올리기
     * [POST] /market
     */
    public ResponseEntity uploadMarket(@RequestBody UploadMarketReq uploadMarketReq) throws BaseException {
        Long userId = jwtService.getUserId();
        marketService.uploadMarket(userId, uploadMarketReq);
        return new ResponseEntity(200, HttpStatus.OK);
    }
}
