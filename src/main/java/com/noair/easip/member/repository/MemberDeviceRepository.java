package com.noair.easip.member.repository;

import com.noair.easip.member.domain.MemberDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberDeviceRepository extends JpaRepository<MemberDevice, String> {
    List<MemberDevice> findAllByMemberId(String memberId);
}
