package med.voll.api.repository;

import med.voll.api.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    @Query("""
                        select m from Medico m
                        where
                        m.ativo = true
                        and
                        m.especialidade = :especialidade
                        and
                        m.id not in(
                            select c.medico.id from Consulta c
                            where
                            c.data = :data
                    and
                            c.motivoCancelamento is null
                        )
                        order by rand()
                        limit 1
            """)
    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

}
