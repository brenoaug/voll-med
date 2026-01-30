package med.voll.api.service.validations.agendamento;

import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.service.ValidadorAgendamentoDeConsulta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo  implements ValidadorAgendamentoDeConsulta {

    private MedicoRepository medicoRepository;

    public ValidadorMedicoAtivo(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }


    public void validar(DadosAgendamentoConsulta dados) {

    }
}
