package br.com.itau.controller;

import br.com.itau.request.TransacaoRequest;
import br.com.itau.service.TransacaoService;
import br.com.itau.service.exeption.RegraDeNegocioExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    public static final String VAZIO = "";
    @Autowired
    private TransacaoService service;

    @PostMapping
    public ResponseEntity<Void> criarTransacoes(@RequestBody @Valid TransacaoRequest request) {
        service.salvarTransacoes(request);
        return ResponseEntity.created(URI.create(VAZIO)).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTransacoes() {
        service.deletarTransacoes();
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({ RegraDeNegocioExeption.class })
    public ResponseEntity<Object> handleValorOuDataHoraExeption(){
        return ResponseEntity.unprocessableEntity().body("");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleJsonInvalido(){
        return ResponseEntity.badRequest().build();
    }
}
