package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.dto.enums.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {
}