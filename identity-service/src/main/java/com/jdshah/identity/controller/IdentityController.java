package com.jdshah.identity.controller;

import com.jdshah.identity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/identity")
@RequiredArgsConstructor
public class IdentityController {

    private final JwtUtil jwtUtil;

    @SneakyThrows
    @GetMapping("/{username}")
    public String generateToken(@PathVariable String username) {
        return jwtUtil.generateToken(new User(username, "1234", Collections.emptyList()));
    }

}
