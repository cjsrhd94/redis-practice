package com.example.redis.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content, User user) {
        if (!this.user.equals(user)) {
            throw new RuntimeException("사용자가 일치하지 않습니다.");
        }
        this.title = title;
        this.content = content;
    }

    public void isDeleteBy(User user) {
        if (!this.user.equals(user)) {
            throw new RuntimeException("사용자가 일치하지 않습니다.");
        }
    }
}
