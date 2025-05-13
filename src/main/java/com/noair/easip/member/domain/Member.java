package com.noair.easip.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
public class Member extends DeletableBaseEntity {

    @Id
    @Column(length = 26, columnDefinition = "CHAR(26)")
    private String id;

    @Column(nullable = false)
    private String name;
}
