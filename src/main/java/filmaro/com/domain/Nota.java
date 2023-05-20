package filmaro.com.domain;

import filmaro.com.exception.MediaInvalidaException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Nota {

    private final double nota;
    private final UUID hashCurso;

    public Nota(double nota, UUID hashCurso) {
        this.nota = nota;
        this.hashCurso = hashCurso;
    }

    public boolean isReprovado(){
        if (nota < 0 || nota > 10)
            throw new MediaInvalidaException(String.format("Média %.2f está fora do intervalo entre 0 e 10", nota));

        return nota < 7;
    }
}
