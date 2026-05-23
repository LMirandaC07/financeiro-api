package com.luis.financeiro.controller;

import com.luis.financeiro.dto.ResumoResponse;
import com.luis.financeiro.dto.TransacaoRequest;
import com.luis.financeiro.model.Transacao;
import com.luis.financeiro.model.Usuario;
import com.luis.financeiro.security.JwtUtil;
import com.luis.financeiro.service.TransacaoService;
import com.luis.financeiro.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public TransacaoController(TransacaoService transacaoService, UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.transacaoService = transacaoService;
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    private Usuario getUsuarioDoToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extrairEmail(token);
        return usuarioService.buscarPorEmail(email);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestHeader("Authorization") String authHeader,
                                   @RequestBody TransacaoRequest request) {
        Usuario usuario = getUsuarioDoToken(authHeader);
        Transacao transacao = transacaoService.criar(request, usuario);
        return ResponseEntity.ok(transacao);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listar(@RequestHeader("Authorization") String authHeader) {
        Usuario usuario = getUsuarioDoToken(authHeader);
        return ResponseEntity.ok(transacaoService.listarPorUsuario(usuario.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable Long id,
                                       @RequestBody TransacaoRequest request) {
        Transacao transacao = transacaoService.atualizar(id, request);
        return ResponseEntity.ok(transacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        transacaoService.deletar(id);
        return ResponseEntity.ok(Map.of("mensagem", "Transação deletada!"));
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoResponse> resumo(@RequestHeader("Authorization") String authHeader) {
        Usuario usuario = getUsuarioDoToken(authHeader);
        return ResponseEntity.ok(transacaoService.resumo(usuario.getId()));
    }
}