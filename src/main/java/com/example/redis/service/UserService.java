package com.example.redis.service;

import com.example.redis.common.redis.CacheKey;
import com.example.redis.domain.User;
import com.example.redis.domain.dto.req.UserJoinReqDto;
import com.example.redis.domain.dto.req.UserUpdateReqDto;
import com.example.redis.domain.dto.res.UserResDto;
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
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(UserJoinReqDto joinReqDto) {
        User user = userRepository.save(joinReqDto.toEntity());
        return user.getId();
    }

    @CachePut(value = CacheKey.USER, key = "#id")
    @Transactional
    public UserResDto update(Long id, UserUpdateReqDto updateReqDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        user.update(updateReqDto.getNickname(), updateReqDto.getPassword());
        return new UserResDto(user);
    }

    @CacheEvict(value = CacheKey.USER, key = "#id")
    @Transactional
    public Long delete(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    @Cacheable(value = CacheKey.USER, key = "#id", unless = "#result == null")
    @Transactional(readOnly = true)
    public UserResDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        return new UserResDto(user);
    }

    @Cacheable(value = CacheKey.USER, unless = "#result == null")
    @Transactional(readOnly = true)
    public List<UserResDto> getUserList() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResDto::new)
                .collect(Collectors.toList());
    }
}
