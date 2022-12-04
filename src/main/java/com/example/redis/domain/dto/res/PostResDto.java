package com.example.redis.domain.dto.res;

import com.example.redis.domain.Post;
import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostResDto implements Serializable {

    private String title;
    private String content;

    @Builder
    public PostResDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
