package com.luis.financeiro.repository;

import com.luis.financeiro.model.Transacao;
import com.luis.financeiro.model.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByUsuarioId(Long usuarioId);
    List<Transacao> findByUsuarioIdAndTipo(Long usuarioId, TipoTransacao tipo);

    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t WHERE t.usuario.id = :usuarioId AND t.tipo = :tipo")
    BigDecimal somarPorTipo(@Param("usuarioId") Long usuarioId, @Param("tipo") TipoTransacao tipo);
}