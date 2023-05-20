package filmaro.com.host.dto.request;

import filmaro.com.domain.Aluno;
import filmaro.com.domain.Curso;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CriarAlunoRequest {

    @NotBlank
    private String nome;

    @NotNull
    private String idCursoInicial;

    public Aluno convertToAluno() {
        return new Aluno(nome, Curso.builder().id(UUID.fromString(idCursoInicial)).build());
    }
}
