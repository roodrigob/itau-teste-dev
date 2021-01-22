package br.com.itau;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItauApplication {

    private static final Logger logger = LoggerFactory.getLogger(ItauApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ItauApplication.class, args);
        logger.info("Iniciando api Itaú");
    }

}
