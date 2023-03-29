package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes relacionados a classe Aluno")
class AlunoTest {


    @Test
    @DisplayName("DADO um aluno com media maior ou igual a 7 " +
                 "QUANDO finalizar o curso " +
                 "ENTÃO deve ser disponibilizado a ele mais 3 cursos")
    void test1() {
        Curso curso = new Curso("Fisica");
        Aluno aluno = new Aluno("Claudio", curso);

        aluno.finalizarCurso(curso);
    }
}