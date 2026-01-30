package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DadosAtualizacaoPaciente;
import med.voll.api.dto.DadosCadastroPaciente;
import med.voll.api.dto.DadosDetalhamentoPaciente;
import med.voll.api.dto.DadosListagemPaciente;
import med.voll.api.entities.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacientesController {
    private static final Logger log = LoggerFactory.getLogger(PacientesController.class);

    private final PacienteRepository repository;

    public PacientesController(PacienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        log.info(dados.toString());

        var paciente = new Paciente(dados);
        repository.save(new Paciente(dados));

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }


    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 12, sort = {"nome"}) Pageable paginacao) {
        log.info("Listagem de pacientes");
        return ResponseEntity.ok().body(repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        log.info(dados.toString());
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> remover(@PathVariable Long id) {
        log.info("Remoção de paciente");
        var paciente = repository.getReferenceById(id);
        paciente.inativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        log.info("Detalhamento de paciente");
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
