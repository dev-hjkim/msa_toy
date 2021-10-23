package com.fashion.celebrity.auth.jpa.controller.dto;

import com.fashion.celebrity.auth.jpa.domain.auth.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateMailRequestDto {
    private String userId;

    @Builder
    public ValidateMailRequestDto(String userId) {
        this.userId = userId;
    }
    public Users toEntity() {
        return Users.builder()
                .userId(userId)
                .build();
    }
}
