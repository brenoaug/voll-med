package med.voll.api.service;

import med.voll.api.dto.DadosCancelamentoConsulta;
import med.voll.api.exception.ValidacaoException;

public interface ValidadorCancelamentoDeConsulta {
    void validar(DadosCancelamentoConsulta dados) throws ValidacaoException;
}
