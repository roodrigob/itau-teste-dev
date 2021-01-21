package br.com.itau.service;

import br.com.itau.request.TransacaoRequest;
import br.com.itau.response.EstatisticaResponse;
import br.com.itau.service.exeption.RegraDeNegocioExeption;
import br.com.itau.utils.BusinessError;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class TransacaoService {

    public static final double VALOR_MINIMO_TRANSACAO = 0;

    private List<TransacaoRequest> listaTransacoes = new ArrayList<>();


    public void salvarTransacoes(final TransacaoRequest request) {

        validarTransacao(request);

        listaTransacoes.add(request);

    }

    private void validarTransacao(final TransacaoRequest request) {

        Optional.ofNullable(request.getValor())
                .orElseThrow(() -> new RegraDeNegocioExeption(BusinessError.VALOR_NAO_ENCONTRADO));

        Optional.ofNullable(request.getValor())
                .filter(valorTransacao -> valorTransacao >= VALOR_MINIMO_TRANSACAO)
                .orElseThrow(() -> new RegraDeNegocioExeption(BusinessError.VALOR_ABAIXO_DO_MINIMO));

        Optional.ofNullable(request.getDataHora())
                .orElseThrow(() -> new RegraDeNegocioExeption(BusinessError.DATA_HORA_NAO_ENCONTRADA));

        Optional.ofNullable(request.getDataHora())
                .filter(transacaoData -> transacaoData.isBefore(OffsetDateTime.now()))
                .orElseThrow(() -> new RegraDeNegocioExeption(BusinessError.DATA_HORA_INCORRETA));

    }


    public void deletarTransacoes() {

        listaTransacoes.removeAll(listaTransacoes);
    }

    public EstatisticaResponse consultarEstatisticas() {

        val listaFiltrada = filtrarListaComTrasacoesAteUmMinuto();

        if (listaFiltrada.isEmpty()) {
            return EstatisticaResponse.builder().build();
        }

        return criarEstatisticaResponse(listaFiltrada);

    }

    private List<TransacaoRequest> filtrarListaComTrasacoesAteUmMinuto() {
        return listaTransacoes.stream()
                .filter(data -> data.getDataHora().isAfter(OffsetDateTime.now().minusSeconds(60)))
                        .collect(Collectors.toList());

    }

    private EstatisticaResponse criarEstatisticaResponse(final List<TransacaoRequest> lista) {

        val Estatisticas = lista.stream().collect(Collectors.summarizingDouble(TransacaoRequest::getValor));

        EstatisticaResponse EstatisticasTratadas = new EstatisticaResponse();

        BeanUtils.copyProperties(Estatisticas, EstatisticasTratadas);

        return EstatisticasTratadas;
    }
}

