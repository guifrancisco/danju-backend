package org.github.guifrancisco.danju.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.github.guifrancisco.danju.domain.dto.DataAuthentication;
import org.github.guifrancisco.danju.domain.dto.DataLoginResponse;
import org.github.guifrancisco.danju.domain.entity.User;
import org.github.guifrancisco.danju.infra.config.TokenManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;


    public AuthenticationController(AuthenticationManager authenticationManager, TokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
    }

    @PostMapping("/login")
    public ResponseEntity<DataLoginResponse> login(@RequestBody @Valid DataAuthentication dataAuthentication){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dataAuthentication.login(),
                dataAuthentication.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenManager.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new DataLoginResponse(token));
    }

}
