package br.com.itau.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoRequest {

    @NotNull(message = "Valor é um campo obrigatório")
    private Double valor;

    @NotNull(message = "Data e hora são obrigatórios")
    private OffsetDateTime dataHora;
}
