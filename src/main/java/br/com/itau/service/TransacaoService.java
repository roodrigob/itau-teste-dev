package br.com.itau.service;

import br.com.itau.request.TransacaoRequest;
import br.com.itau.service.exeption.RegraDeNegocioExeption;
import br.com.itau.utils.BusinessError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
}

