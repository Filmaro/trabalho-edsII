package filmaro.com.gateway.repository.entity;

import filmaro.com.domain.Aluno;
import filmaro.com.domain.Curso;
import filmaro.com.domain.Nota;
import filmaro.com.domain.enums.TipoAssinaturaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "aluno")
public class AlunoEntity {

    @Id
    private String id;
    private String nome;
    private String tipoAssinatura;
    private Integer qtdCursosDisponiveis;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "aluno_curso",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<CursoEntity> cursosAdicionados;

    @ElementCollection
    private Map<String, Double> notas;

    public Aluno convertToAluno() {
        Map<UUID, Curso> cursosAdicionados = new HashMap<>();
        this.cursosAdicionados.forEach(x -> cursosAdicionados.put(UUID.fromString(x.getId()), x.convertToCurso()));

        List<Nota> notaList = new ArrayList<>();
        notas.forEach((x, y) -> new Nota(y, UUID.fromString(x)));

        return Aluno.builder()
                .cursosAdicionados(cursosAdicionados)
                .id(UUID.fromString(id))
                .nome(nome)
                .notas(notaList)
                .qtdCursosDisponiveis(qtdCursosDisponiveis)
                .tipoAssinatura(TipoAssinaturaEnum.valueOf(tipoAssinatura))
                .build();
    }
}
