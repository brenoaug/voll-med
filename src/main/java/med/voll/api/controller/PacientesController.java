package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosCadastroPaciente;
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

@RestController
@RequestMapping("/pacientes")
public class PacientesController {
    private static final Logger log = LoggerFactory.getLogger(PacientesController.class);

    private final PacienteRepository repository;

    public PacientesController(PacienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
        log.info(dados.toString());
        repository.save(new Paciente(dados));
        return ResponseEntity.ok().body("Paciente cadastrado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 12, sort = {"nome"}) Pageable paginacao) {
        log.info("Listagem de pacientes");
        return ResponseEntity.ok().body(repository.findAll(paginacao).map(DadosListagemPaciente::new));
    }

}
