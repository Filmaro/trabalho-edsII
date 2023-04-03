package domain;

import exception.MediaInvalidaException;
import lombok.Data;

import java.util.UUID;

@Data
public class Curso {

    private UUID id;
    private String nome;
    private double nota;

    public Curso(String nome) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.nota = -1;
    }

    public void completarCurso(double nota) {
        this.nota = nota;
    }

    public boolean isReprovado(){
        if (nota < 0 || nota > 10)
            throw new MediaInvalidaException(String.format("Média %.2f está fora do intervalo entre 0 e 10", nota));

        return nota < 7;
    }
}
