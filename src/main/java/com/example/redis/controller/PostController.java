package com.example.redis.controller;

import com.example.redis.domain.dto.req.PostUpdateReqDto;
import com.example.redis.domain.dto.req.PostWriteReqDto;
import com.example.redis.domain.dto.res.PostResDto;
import com.example.redis.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/user/{userId}/post")
    public Long write(@PathVariable Long userId,
                      @RequestBody PostWriteReqDto postWriteReqDto) {
        return postService.write(userId, postWriteReqDto);
    }

    @PutMapping("/user/{userId}/post/{postId}")
    public Long update(@PathVariable Long userId,
                       @PathVariable Long postId,
                       @RequestBody PostUpdateReqDto postUpdateReqDto) {
        return postService.update(userId, postId, postUpdateReqDto);
    }

    @DeleteMapping("/user/{userId}/post/{postId}")
    public Long delete(@PathVariable Long userId,
                       @PathVariable Long postId) {
        return postService.delete(userId, postId);
    }

    @GetMapping("/post/{postId}")
    public PostResDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @GetMapping("/post")
    public List<PostResDto> getPostList() {
        return postService.getPostList();
    }
}
