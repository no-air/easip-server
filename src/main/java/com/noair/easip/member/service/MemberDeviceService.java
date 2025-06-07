package com.noair.easip.member.service;

import com.noair.easip.member.domain.Member;
import com.noair.easip.member.domain.MemberDevice;
import com.noair.easip.member.exception.MemberDeviceNotFoundException;
import com.noair.easip.member.repository.MemberDeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDeviceService {
    private final MemberDeviceRepository memberDeviceRepository;

    public void registerFcmToken(String fcmToken, Member member) {
        memberDeviceRepository.save(
                MemberDevice.builder()
                        .fcmToken(fcmToken)
                        .member(member)
                        .fcmTransmissionFailCnt(0)
                        .build()
        );
    }

    public MemberDevice getMemberDevice(String fcmToken) {
        return memberDeviceRepository.findById(fcmToken)
                .orElseThrow(MemberDeviceNotFoundException::new);
    }

    public List<String> getFcmTokensByMemberId(String memberId) {
        return memberDeviceRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberDevice::getFcmToken)
                .toList();
    }

    @Transactional
    public void recordFcmTransmissionFail(String fcmToken) {
        MemberDevice memberDevice = getMemberDevice(fcmToken);
        memberDevice.recordFcmTransmissionFail();
    }
}
