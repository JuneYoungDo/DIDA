package com.service.dida.Utils.appManage;

import com.service.dida.Utils.appManage.DTO.GetAppVersionDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppManageService {

    private final VersionRepository versionRepository;

    public GetAppVersionDto getAppVersion(Long versionId) {
        return new GetAppVersionDto(versionRepository.getVersionFromId(versionId).orElse(0L));
    }
}
