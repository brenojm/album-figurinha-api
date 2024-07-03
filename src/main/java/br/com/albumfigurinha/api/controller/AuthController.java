package br.com.albumfigurinha.api.controller;

import br.com.albumfigurinha.api.config.auth.TokenProvider;
import br.com.albumfigurinha.api.dto.JwtDTO;
import br.com.albumfigurinha.api.dto.SignInDTO;
import br.com.albumfigurinha.api.entity.User;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenService;

    @PostMapping("/signin")
    public ResponseEntity<JwtDTO> signIn(@RequestBody SignInDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        var authUser = authenticationManager.authenticate(usernamePassword);
        var accessToken = tokenService.generateAccessToken((User) authUser.getPrincipal());
        JwtDTO accessTokenDTO = new JwtDTO(accessToken);
        return ResponseEntity.ok(accessTokenDTO);
    }
}

