package br.com.itau.controller;

import br.com.itau.request.TransacaoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/transacao")
public class TransacaoControle {

    @PostMapping
    public ResponseEntity<Void> criarTransacoes(@RequestBody @Valid  TransacaoRequest request) {
        URI uri = URI.create("");
        return ResponseEntity.created(uri).build();
    }
}
