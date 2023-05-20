package domain;

import filmaro.com.domain.Aluno;
import filmaro.com.domain.Curso;
import filmaro.com.domain.Nota;
import filmaro.com.exception.MediaInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes relacionados a classe Aluno")
class AlunoTest {

    @Test
    @DisplayName("DADO um aluno com media maior ou igual a 7 " +
                 "QUANDO finalizar o curso " +
                 "ENTÃO deve ser disponibilizado a ele mais 3 cursos e deve ser retornado true")
    void test1() {
        Curso curso = new Curso("Fisica");
        Aluno aluno = new Aluno("Claudio", curso);

        assertEquals(0, aluno.getQtdCursosDisponiveis());
        assertTrue(aluno.finalizarCurso(new Nota(7.0, curso.getId())));
        assertEquals(3, aluno.getQtdCursosDisponiveis());
    }

    @Test
    @DisplayName("DADO um aluno com media menor ou igual a 7 " +
                 "QUANDO finalizar o curso " +
                 "ENTÃO não deve ser disponibilizado a ele mais cursos e deve ser retornado false")
    void test2() {
        Curso curso = new Curso("Fisica");
        Aluno aluno = new Aluno("Claudio", curso);

        assertEquals(0, aluno.getQtdCursosDisponiveis());
        assertFalse(aluno.finalizarCurso(new Nota(6.9, curso.getId())));
        assertEquals(0, aluno.getQtdCursosDisponiveis());
    }

    @Test
    @DisplayName("DADO um aluno com media inválida " +
                 "QUANDO finalizar o curso " +
                 "ENTÃO deve ser lançado uma exception de média inválida e não deve ser disponibilizado novos cursos")
    void test3() {
        Curso curso = new Curso("Fisica");
        Aluno aluno = new Aluno("Claudio", curso);

        assertThrows(MediaInvalidaException.class, () -> aluno.finalizarCurso(new Nota(-10, curso.getId())));
        assertEquals(0, aluno.getQtdCursosDisponiveis());
    }
}