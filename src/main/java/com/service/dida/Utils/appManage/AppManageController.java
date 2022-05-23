package com.service.dida.Utils.appManage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppManageController {

    private final AppManageService appManageService;

    /**
     * 앱 최신 버전 확인 api
     * [GET] /app/version
     */
    @GetMapping("/app/version")
    public ResponseEntity getAppVersion() {
        return new ResponseEntity(appManageService.getAppVersion(0L), HttpStatus.valueOf(200));
    }
}
