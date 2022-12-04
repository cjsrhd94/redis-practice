package com.example.redis.domain.dto.req;

import com.example.redis.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJoinReqDto {

    private String nickname;
    private String password;

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .password(password)
                .build();
    }
}
