package filmaro.com.gateway.repository.entity;

import filmaro.com.domain.Curso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "curso")
public class CursoEntity {

    @Id
    private String id;
    private String nome;

    @ManyToMany(mappedBy = "cursosAdicionados")
    private List<AlunoEntity> alunoEntityList;

    public Curso convertToCurso() {
        return Curso.builder()
                .id(UUID.fromString(id))
                .nome(nome)
                .build();
    }
}
