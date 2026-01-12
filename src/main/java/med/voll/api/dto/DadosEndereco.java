package med.voll.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.dto.enums.Uf;

public record DadosEndereco(
        @NotBlank
        String logradouro,
        String numero,
        String complemento,
        @NotBlank
        String bairro,
        @NotBlank
        String cidade,
        @NotNull
        Uf uf,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep) {
}
