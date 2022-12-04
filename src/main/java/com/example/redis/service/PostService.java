package com.example.redis.service;

import com.example.redis.common.redis.CacheKey;
import com.example.redis.domain.Post;
import com.example.redis.domain.User;
import com.example.redis.domain.dto.req.PostUpdateReqDto;
import com.example.redis.domain.dto.req.PostWriteReqDto;
import com.example.redis.domain.dto.res.PostResDto;
import com.example.redis.repository.PostRepository;
import com.example.redis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long write(Long userId, PostWriteReqDto postWriteReqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        Post post = postRepository.save(postWriteReqDto.toEntity(user));
        return post.getId();
    }

    @CachePut(value = CacheKey.POST, key = "#postId")
    @Transactional
    public Long update(Long userId, Long postId, PostUpdateReqDto postUpdateReqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));
        post.update(postUpdateReqDto.getTitle(), postUpdateReqDto.getContent(), user);
        return post.getId();
    }

    @CacheEvict(value = CacheKey.POST, key = "#postId")
    @Transactional
    public Long delete(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));
        post.isDeleteBy(user);
        postRepository.deleteById(postId);
        return postId;
    }

    @Cacheable(value = CacheKey.POST, key = "#postId", unless = "#result == null")
    @Transactional(readOnly = true)
    public PostResDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));
        return new PostResDto(post);
    }

    @Cacheable(value = CacheKey.POST, unless = "#result == null")
    @Transactional(readOnly = true)
    public List<PostResDto> getPostList() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResDto::new)
                .collect(Collectors.toList());
    }
}
