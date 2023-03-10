package com.alexgim.filterinterceptor.controller;

import com.alexgim.filterinterceptor.dto.UserPostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody UserPostDto userPostDto) {
        log.info("join API start");
        log.info("join API end");
        return ResponseEntity.status(HttpStatus.OK).body(userPostDto);
    }
}
