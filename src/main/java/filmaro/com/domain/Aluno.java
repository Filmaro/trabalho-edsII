package filmaro.com.domain;

import filmaro.com.domain.enums.TipoAssinaturaEnum;
import filmaro.com.exception.CursosDisnoniveisInvalidosException;
import filmaro.com.gateway.repository.entity.AlunoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class Aluno {

    private UUID id;
    private String nome;
    private Map<UUID, Curso> cursosAdicionados;
    private TipoAssinaturaEnum tipoAssinatura;
    private int qtdCursosDisponiveis;
    private List<Nota> notas;

    public Aluno(String nome, Curso cursoInicial) {
        this.id = UUID.randomUUID();
        this.qtdCursosDisponiveis = 1;
        this.nome = nome;
        this.tipoAssinatura = TipoAssinaturaEnum.BASICO;

        this.cursosAdicionados = new HashMap<>();
        this.notas = new ArrayList<>();
        addCurso(cursoInicial);
    }

    public void addCurso(Curso curso) {
        if (qtdCursosDisponiveis == 0)
            throw new CursosDisnoniveisInvalidosException("Não há cursos disponíveis para esse Aluno");

        this.cursosAdicionados.put(curso.getId(), curso);
        qtdCursosDisponiveis--;
    }

    public boolean finalizarCurso(Nota nota) {
        notas.add(nota);
        if (nota.isReprovado())
            return false;

        qtdCursosDisponiveis += 3;
        return true;
    }

    public AlunoEntity convertToEntity() {
        return AlunoEntity.builder()
                .id(id.toString())
                .nome(nome)
                .tipoAssinatura(tipoAssinatura.toString())
                .qtdCursosDisponiveis(qtdCursosDisponiveis)
                .cursosAdicionados(cursosAdicionados.values().stream().map(Curso::convertToEntity).collect(Collectors.toList()))
                .build();
    }
}
