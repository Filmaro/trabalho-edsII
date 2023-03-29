package domain;

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

    public boolean isCursoFinalizado() {
        return nota != -1;
    }

    public boolean isReprovado(){
        if (!isCursoFinalizado())
            throw new IllegalArgumentException();

        return nota < 7;
    }
}
