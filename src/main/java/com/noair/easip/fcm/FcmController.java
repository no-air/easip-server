package com.noair.easip.fcm;

import com.noair.easip.fcm.dto.FcmSendDto;
import com.noair.easip.util.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@Tag(name = "FCM API")
@RequestMapping("/v1/fcm")
@RequiredArgsConstructor
public class FcmController {

    private final FcmMessagingService fcmService;

    @PostMapping("/send")
    @Operation(summary = "내 프로필 조회")
    public DefaultResponse pushMessage(@RequestBody @Validated FcmSendDto fcmSendDto) throws IOException {
        Boolean result = fcmService.sendMessageTo(fcmSendDto);
        log.debug("[+] 푸시 메시지를 전송합니다. " + result.toString());
        return DefaultResponse.ok();
    }
}