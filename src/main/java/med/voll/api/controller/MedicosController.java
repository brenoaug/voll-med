package med.voll.api.controller;

import med.voll.api.dto.DadosCadastroMedico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicosController {
    private static final Logger log = LoggerFactory.getLogger(MedicosController.class);

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody DadosCadastroMedico dados){
        log.info(dados.toString());
        return ResponseEntity.ok().body("Médico cadastrado com sucesso!");
    }
}
