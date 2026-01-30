package med.voll.api.service.validations.cancelamento;

import med.voll.api.dto.DadosCancelamentoConsulta;
import med.voll.api.exception.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.service.ValidadorCancelamentoDeConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta {

        private ConsultaRepository repository;

        public ValidadorHorarioAntecedencia(ConsultaRepository repository) {
            this.repository = repository;
        }

        @Override
        public void validar(DadosCancelamentoConsulta dados) throws ValidacaoException {
            var consulta = repository.getReferenceById(dados.idConsulta());
            var agora = LocalDateTime.now();
            var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

            if (diferencaEmHoras < 24) {
                throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
            }
        }
}
