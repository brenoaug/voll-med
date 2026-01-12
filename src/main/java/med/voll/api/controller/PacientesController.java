package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosCadastroPaciente;
import med.voll.api.entities.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
