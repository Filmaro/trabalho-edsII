package filmaro.com.host.dto.request;

import filmaro.com.domain.Nota;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
public class FinalizarCursoRequest {

    @NotBlank
    private String idCursoFinalizado;
    @Min(value = 0)
    @Max(value = 10)
    private Double notaCurso;
    @Size(max = 3)
    private List<String> idsCursosDesejados;

    public Nota criarNota() {
        return new Nota(notaCurso, UUID.fromString(idCursoFinalizado));
    }
}
