package domain;

import exception.AlunoReprovadoException;
import exception.MediaInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes relacionados a classe Aluno")
class AlunoTest {


    @Test
    @DisplayName("DADO um aluno com media maior ou igual a 7 " +
                 "QUANDO finalizar o curso " +
                 "ENTÃO deve ser disponibilizado a ele mais 3 cursos")
    void test1() {
        Curso curso = new Curso("Fisica");
        Aluno aluno = new Aluno("Claudio", curso);

        assertEquals(0, aluno.getQtdCursosDisponiveis());

        curso.setNota(7.0);
        aluno.finalizarCurso(curso);

        assertEquals(3, aluno.getQtdCursosDisponiveis());
    }

    @Test
    @DisplayName("DADO um aluno com media menor ou igual a 7 " +
                 "QUANDO finalizar o curso " +
                 "ENTÃO não deve ser disponibilizado a ele mais cursos e deve ser lançado uma exception com a reprovação")
    void test2() {
        Curso curso = new Curso("Fisica");
        Aluno aluno = new Aluno("Claudio", curso);

        assertEquals(0, aluno.getQtdCursosDisponiveis());

        curso.setNota(6.9);
        
        assertThrows(AlunoReprovadoException.class, () -> aluno.finalizarCurso(curso));
        assertEquals(0, aluno.getQtdCursosDisponiveis());
    }

    @Test
    @DisplayName("DADO um aluno com media inválida " +
                 "QUANDO finalizar o curso " +
                 "ENTÃO deve ser lançado uma exception de média inválida e não deve ser disponibilizado novos cursos")
    void test3() {
        Curso curso = new Curso("Fisica");
        Aluno aluno = new Aluno("Claudio", curso);

        curso.setNota(-10);

        assertThrows(MediaInvalidaException.class, () -> aluno.finalizarCurso(curso));
        assertEquals(0, aluno.getQtdCursosDisponiveis());
    }
}