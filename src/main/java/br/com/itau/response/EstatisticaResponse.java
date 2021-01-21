package br.com.itau.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstatisticaResponse {

    @Builder.Default
    private Long count = 0L;

    @Builder.Default
    private Double sum = 0D;

    @JsonProperty("avg")
    @Builder.Default
    private Double average = 0D;

    @Builder.Default
    private Double min = 0D;

    @Builder.Default
    private Double max = 0D;

}
