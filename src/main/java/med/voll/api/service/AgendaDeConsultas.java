package med.voll.api.service;

import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.dto.DadosCancelamentoConsulta;
import med.voll.api.dto.DadosDetalhamentoConsulta;
import med.voll.api.entities.Consulta;
import med.voll.api.entities.Medico;
import med.voll.api.exception.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorAgendamentoDeConsulta> validadores;
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public AgendaDeConsultas(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, List<ValidadorAgendamentoDeConsulta> validadores, List<ValidadorCancelamentoDeConsulta> validadoresCancelamento) {
        this.validadoresCancelamento = validadoresCancelamento;
        this.validadores = validadores;
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) throws ValidacaoException {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validadores.forEach(v -> {
            try {
                v.validar(dados);
            } catch (ValidacaoException e) {
                throw new RuntimeException(e);
            }
        });

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = escolherMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) throws ValidacaoException {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) throws ValidacaoException {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> {
            try {
                v.validar(dados);
            } catch (ValidacaoException e) {
                throw new RuntimeException(e);
            }
        });

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

}