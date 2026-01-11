package med.voll.api.dto;

import med.voll.api.dto.enums.Uf;

public record DadosEndereco(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        Uf uf,
        String cep) {
}
