package br.com.itau.service.exeption;

import lombok.Getter;

@Getter
public class RegraDeNegocioExeption extends RuntimeException{

        private static final long serialVersionUID = 1L;

        private String code;

        public RegraDeNegocioExeption(final String code) {
            this.code = code;
        }

    }

