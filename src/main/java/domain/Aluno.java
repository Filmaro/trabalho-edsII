package domain;

import exception.AlunoReprovadoException;

import java.util.*;

public class Aluno {

    private String nome;
    private Map<UUID, Curso> cursosAdicionados;
    private TipoAssinaturaEnum tipoAssinatura;
    private int qtdCursosDisponiveis;

    public Aluno(String nome, Curso cursoInicial) {
        this.qtdCursosDisponiveis = 1;
        this.nome = nome;
        this.tipoAssinatura = TipoAssinaturaEnum.BASICO;

        this.cursosAdicionados = new HashMap<>();
        addCurso(cursoInicial);
    }

    public void addCurso(Curso curso) {
        if (qtdCursosDisponiveis == 0)
            throw new IllegalArgumentException();

        this.cursosAdicionados.put(curso.getId(), curso);
        qtdCursosDisponiveis--;
    }

    public void finalizarCurso(Curso curso) {
        if (curso.isReprovado())
            throw new AlunoReprovadoException(String.format("A média do aluno %s ficou abaixo de 7 no curso %s, e foi reprovado", nome, curso.getNome()));

        qtdCursosDisponiveis += 3;
    }

    public int getQtdCursosDisponiveis() {
        return this.qtdCursosDisponiveis;
    }
}
