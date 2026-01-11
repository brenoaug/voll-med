package med.voll.api.dto;

import med.voll.api.dto.enums.Especialidade;

public record DadosCadastroMedico(
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        DadosEndereco endereco) {
}
