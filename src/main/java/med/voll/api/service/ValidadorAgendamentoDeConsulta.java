package med.voll.api.service;

import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.exception.ValidacaoException;

public interface ValidadorAgendamentoDeConsulta {
    void validar(DadosAgendamentoConsulta dados) throws ValidacaoException;
}
