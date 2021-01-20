package br.com.itau.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransacaoControle {

    @GetMapping
    public ResponseEntity<String> criarTransacoes() {
        return ResponseEntity.ok("funciounou");
    }
}
