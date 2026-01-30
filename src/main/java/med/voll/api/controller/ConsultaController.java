package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.dto.DadosAgendamentoConsulta;
import med.voll.api.dto.DadosCancelamentoConsulta;
import med.voll.api.dto.DadosDetalhamentoConsulta;
import med.voll.api.exception.ValidacaoException;
import med.voll.api.service.AgendaDeConsultas;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    private final AgendaDeConsultas agendaDeConsultas;

    public ConsultaController(AgendaDeConsultas agendaDeConsultas) {
        this.agendaDeConsultas = agendaDeConsultas;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) throws ValidacaoException {
        var dto = agendaDeConsultas.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<DadosCancelamentoConsulta> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) throws ValidacaoException {
        agendaDeConsultas.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
