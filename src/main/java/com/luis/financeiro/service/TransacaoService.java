package com.luis.financeiro.service;

import com.luis.financeiro.dto.ResumoResponse;
import com.luis.financeiro.dto.TransacaoRequest;
import com.luis.financeiro.model.Transacao;
import com.luis.financeiro.model.TipoTransacao;
import com.luis.financeiro.model.Usuario;
import com.luis.financeiro.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public Transacao criar(TransacaoRequest request, Usuario usuario) {
        Transacao transacao = new Transacao();
        transacao.setDescricao(request.getDescricao());
        transacao.setValor(request.getValor());
        transacao.setData(request.getData());
        transacao.setTipo(request.getTipo());
        transacao.setCategoria(request.getCategoria());
        transacao.setUsuario(usuario);
        return transacaoRepository.save(transacao);
    }

    public List<Transacao> listarPorUsuario(Long usuarioId) {
        return transacaoRepository.findByUsuarioId(usuarioId);
    }

    public Transacao buscarPorId(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada!"));
    }

    public Transacao atualizar(Long id, TransacaoRequest request) {
        Transacao transacao = buscarPorId(id);
        transacao.setDescricao(request.getDescricao());
        transacao.setValor(request.getValor());
        transacao.setData(request.getData());
        transacao.setTipo(request.getTipo());
        transacao.setCategoria(request.getCategoria());
        return transacaoRepository.save(transacao);
    }

    public void deletar(Long id) {
        transacaoRepository.deleteById(id);
    }

    public ResumoResponse resumo(Long usuarioId) {
        BigDecimal receitas = transacaoRepository.somarPorTipo(usuarioId, TipoTransacao.RECEITA);
        BigDecimal despesas = transacaoRepository.somarPorTipo(usuarioId, TipoTransacao.DESPESA);
        BigDecimal saldo = receitas.subtract(despesas);
        return new ResumoResponse(receitas, despesas, saldo);
    }
}