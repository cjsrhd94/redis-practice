package com.example.redis.controller;

import com.example.redis.domain.dto.req.UserJoinReqDto;
import com.example.redis.domain.dto.req.UserUpdateReqDto;
import com.example.redis.domain.dto.res.UserResDto;
import com.example.redis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public Long join(@RequestBody UserJoinReqDto userJoinReqDto) {
        return userService.join(userJoinReqDto);
    }

    @PutMapping("/{id}")
    public UserResDto update(@PathVariable Long id,
                       @RequestBody UserUpdateReqDto userUpdateReqDto) {
        return userService.update(id, userUpdateReqDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserResDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<UserResDto> getUserList() {
        return userService.getUserList();
    }
}
