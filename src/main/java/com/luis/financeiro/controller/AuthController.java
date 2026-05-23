package com.luis.financeiro.controller;

import com.luis.financeiro.dto.LoginRequest;
import com.luis.financeiro.dto.RegisterRequest;
import com.luis.financeiro.model.Usuario;
import com.luis.financeiro.security.JwtUtil;
import com.luis.financeiro.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            Usuario usuario = usuarioService.cadastrar(request);
            String token = jwtUtil.gerarToken(usuario.getEmail());
            return ResponseEntity.ok(Map.of("token", token, "nome", usuario.getNome()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(request.getEmail());
            if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Senha incorreta!"));
            }
            String token = jwtUtil.gerarToken(usuario.getEmail());
            return ResponseEntity.ok(Map.of("token", token, "nome", usuario.getNome()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}