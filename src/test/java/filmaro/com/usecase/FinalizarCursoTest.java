package filmaro.com.usecase;

import filmaro.com.domain.Aluno;
import filmaro.com.domain.Curso;
import filmaro.com.domain.Nota;
import filmaro.com.gateway.AlunoGateway;
import filmaro.com.gateway.CursoGateway;
import filmaro.com.host.dto.request.FinalizarCursoRequest;
import filmaro.com.usecase.impl.FinalizarCursoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Testes relacionados ao usecase de FinalizarCurso")
class FinalizarCursoTest {

    @Mock
    private AlunoGateway alunoGateway;
    @Mock
    private CursoGateway cursoGateway;

    private FinalizarCurso finalizarCurso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        finalizarCurso = new FinalizarCursoImpl(alunoGateway, cursoGateway);
    }

    @Test
    @DisplayName("DADO um id de um aluno e um FinalizarCursoRequest" +
                 "QUANDO for executado " +
                 "ENTÃO finalizado o curso para o aluno sem erros")
    void test1() {
        UUID idCursoFinalizado = UUID.randomUUID();
        UUID cursoAdicionado = UUID.randomUUID();
        FinalizarCursoRequest finalizarCursoRequest = new FinalizarCursoRequest(idCursoFinalizado.toString(), 7.0, Collections.singletonList(cursoAdicionado.toString()));
        Curso curso = new Curso(cursoAdicionado, "CURSO");
        Aluno aluno = new Aluno("Teste", curso);

        when(alunoGateway.procurarAlunoPorId(Mockito.anyString())).thenReturn(aluno);
        when(alunoGateway.salvarAluno(Mockito.any())).thenReturn(aluno);
        when(cursoGateway.procurarCursoPorId(Mockito.anyString())).thenReturn(curso);

        boolean isFinalizado = finalizarCurso.execute("idTest", finalizarCursoRequest);

        assertTrue(isFinalizado);
        assertTrue(aluno.getNotas().contains(new Nota(7.0, idCursoFinalizado)));

        verify(alunoGateway).procurarAlunoPorId("idTest");
        verify(alunoGateway).salvarAluno(aluno);
        verify(cursoGateway).procurarCursoPorId(cursoAdicionado.toString());
    }

    @Test
    @DisplayName("DADO um id de um aluno e um FinalizarCursoRequest" +
                 "QUANDO for executado e o aluno não for aprovado " +
                 "ENTÃO o aluno não deve finalizar o curso e não deve ser adicionado um novo")
    void test2() {
        UUID idCursoFinalizado = UUID.randomUUID();
        UUID cursoAdicionado = UUID.randomUUID();
        FinalizarCursoRequest finalizarCursoRequest = new FinalizarCursoRequest(idCursoFinalizado.toString(), 6.0, Collections.singletonList(cursoAdicionado.toString()));
        Curso curso = new Curso(cursoAdicionado, "CURSO");
        Aluno aluno = new Aluno("Teste", curso);

        when(alunoGateway.procurarAlunoPorId(Mockito.anyString())).thenReturn(aluno);
        when(alunoGateway.salvarAluno(Mockito.any())).thenReturn(aluno);
        when(cursoGateway.procurarCursoPorId(Mockito.anyString())).thenReturn(curso);

        boolean isFinalizado = finalizarCurso.execute("idTest", finalizarCursoRequest);

        assertFalse(isFinalizado);
        assertTrue(aluno.getNotas().contains(new Nota(6.0, idCursoFinalizado)));

        verify(alunoGateway).procurarAlunoPorId("idTest");
        verify(alunoGateway).salvarAluno(aluno);
        verify(cursoGateway, never()).procurarCursoPorId(Mockito.anyString());
    }
}