package domain;

import java.util.*;

public class Aluno {

    private String nome;
    private Map<UUID, Curso> cursosAdicionados;
    private TipoAssinaturaEnum tipoAssinatura;
    private int qtdCursosDisponiveis;

    public Aluno(String nome, Curso cursoInicial) {
        this.cursosAdicionados = new HashMap<>();
        addCurso(cursoInicial);

        this.nome = nome;
        this.tipoAssinatura = TipoAssinaturaEnum.BASICO;
    }

    public void addCurso(Curso curso) {
        if (qtdCursosDisponiveis == 0)
            throw new IllegalArgumentException();

        this.cursosAdicionados.put(curso.getId(), curso);
        qtdCursosDisponiveis--;
    }

    public void finalizarCurso(Curso curso) {
        if (curso.isReprovado())
            throw new IllegalArgumentException();

        qtdCursosDisponiveis += 3;

        if (cursosCompletados() == 12)
            tipoAssinatura = TipoAssinaturaEnum.PREMIUM;
    }

    public long cursosCompletados() {
        return cursosAdicionados.values().stream().filter(Curso::isCursoFinalizado).count();
    }
}
