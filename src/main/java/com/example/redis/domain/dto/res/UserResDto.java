package com.example.redis.domain.dto.res;

import com.example.redis.domain.User;
import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserResDto implements Serializable {

    private String nickname;

    @Builder
    public UserResDto(User user) {
        this.nickname = user.getNickname();
    }
}
