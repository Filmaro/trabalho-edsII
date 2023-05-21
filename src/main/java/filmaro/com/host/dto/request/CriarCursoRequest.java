package filmaro.com.host.dto.request;

import filmaro.com.domain.Curso;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class CriarCursoRequest {

    @NotBlank
    private String nome;

    public Curso convertToCurso() {
        return Curso.builder()
                .id(UUID.randomUUID())
                .nome(nome)
                .build();
    }
}
