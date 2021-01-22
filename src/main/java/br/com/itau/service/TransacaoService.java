package br.com.itau.service;

import br.com.itau.request.TransacaoRequest;
import br.com.itau.response.EstatisticaResponse;
import br.com.itau.service.exeption.RegraDeNegocioExeption;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransacaoService {

    public static final double VALOR_MINIMO_TRANSACAO = 0;

    private List<TransacaoRequest> listaTransacoes = new ArrayList<>();


    public void salvarTransacoes(final TransacaoRequest request) {

        log.info("Salvando Transações");
        validarTransacao(request);

        log.info("Transação salva");
        listaTransacoes.add(request);

    }

    private void validarTransacao(final TransacaoRequest request) {

        Optional.ofNullable(request.getValor())
                .orElseThrow(() -> {
                    log.info("Valor não pode ser nulo");
                    return new RegraDeNegocioExeption();
                });

        Optional.ofNullable(request.getValor())
                .filter(valorTransacao -> valorTransacao >= VALOR_MINIMO_TRANSACAO)
                .orElseThrow(() -> {
                    log.info("Valor menor que 0 não é permitido");
                    return new RegraDeNegocioExeption();
                });

        Optional.ofNullable(request.getDataHora())
                .orElseThrow(() -> {
                    log.info("Data não pode ser nula");
                    return new RegraDeNegocioExeption();
                });

        Optional.ofNullable(request.getDataHora())
                .filter(transacaoData -> transacaoData.isBefore(OffsetDateTime.now()))
                .orElseThrow(() -> {
                    log.info("Datas futuras não são permitidas");
                    return new RegraDeNegocioExeption();
                });

    }


    public void deletarTransacoes() {

        log.info("Excluindo transações");
        listaTransacoes.removeAll(listaTransacoes);
        log.info("Transações excluídas com Sucesso");
    }

    public EstatisticaResponse consultarEstatisticas(final Long periodo) {

        val listaFiltrada = filtrarListaComTrasacoesPorPeriodo(periodo);
        log.info("Sucesso ao Filtrar transações");

        if (listaFiltrada.isEmpty()) {
            log.info("Lista sem transações. retornando 0 para todas estatísticas");
            return EstatisticaResponse.builder().build();
        }

        return criarEstatisticaResponse(listaFiltrada);

    }

    private List<TransacaoRequest> filtrarListaComTrasacoesPorPeriodo(final Long periodo) {
        log.info("Filtrando transações por período");
        return listaTransacoes.stream()
                .filter(data -> data.getDataHora().isAfter(OffsetDateTime.now().minusSeconds(periodo)))
                .collect(Collectors.toList());

    }

    private EstatisticaResponse criarEstatisticaResponse(final List<TransacaoRequest> lista) {

        log.info("Criando estatísticas");
        val Estatisticas = lista.stream().collect(Collectors.summarizingDouble(TransacaoRequest::getValor));

        EstatisticaResponse EstatisticasTratadas = new EstatisticaResponse();

        log.info("Copiando a classe de estatísticas para objeto de response");
        BeanUtils.copyProperties(Estatisticas, EstatisticasTratadas);

        return EstatisticasTratadas;
    }
}

