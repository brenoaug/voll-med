package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.DadosListagemMedico;
import med.voll.api.entities.Medico;
import med.voll.api.repository.MedicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicosController {
    private static final Logger log = LoggerFactory.getLogger(MedicosController.class);

    private final MedicoRepository repository;

    public MedicosController(MedicoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        log.info(dados.toString());
        repository.save(new Medico(dados));
        return ResponseEntity.ok().body("Médico cadastrado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault( size = 12, sort = {"nome"}) Pageable paginacao) {
        log.info("Listagem de médicos");
        return ResponseEntity.ok(repository.findAll(paginacao).map(DadosListagemMedico::new));
    }
}
