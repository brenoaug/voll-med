package med.voll.api.controller;

import med.voll.api.dto.DadosCadastroPaciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {
    private static final Logger log = LoggerFactory.getLogger(PacientesController.class);

    @PostMapping
    public ResponseEntity<String> cadastrar(DadosCadastroPaciente dados) {
        log.info(dados.toString());
        return ResponseEntity.ok().body("Paciente cadastrado com sucesso!");
    }
}
