package filmaro.com.gateway;

import filmaro.com.domain.Aluno;
import filmaro.com.domain.Curso;
import filmaro.com.exception.DataBaseComunicationException;
import filmaro.com.gateway.impl.AlunoGatewayImpl;
import filmaro.com.gateway.repository.AlunoRepository;
import filmaro.com.gateway.repository.entity.AlunoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlunoGatewayTest {

    @Mock
    private AlunoRepository alunoRepository;

    private AlunoGateway alunoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alunoGateway = new AlunoGatewayImpl(alunoRepository);
    }

    @Test
    @DisplayName("DADO um aluno" +
                 "QUANDO for executado o save no banco " +
                 "ENTÃO o aluno deve ser salvo sem erros")
    void test1() {
        Aluno aluno = new Aluno("teste", new Curso("teste"));

        when(alunoRepository.save(Mockito.any())).thenReturn(null);

        Aluno alunoReturned = alunoGateway.salvarAluno(aluno);

        assertEquals(alunoReturned, aluno);

        verify(alunoRepository).save(refEq(aluno.convertToEntity()));
    }

    @Test
    @DisplayName("DADO um aluno" +
                 "QUANDO for executado o save no banco e for dado um erro ao salvar na base" +
                 "ENTÃO deve ser lançado um DataBaseComunicationException")
    void test2() {
        Aluno aluno = new Aluno("teste", new Curso("teste"));

        when(alunoRepository.save(Mockito.any())).thenThrow(InvalidJpaQueryMethodException.class);

        assertThrows(DataBaseComunicationException.class, () -> alunoGateway.salvarAluno(aluno));

        verify(alunoRepository).save(refEq(aluno.convertToEntity()));
    }

    @Test
    @DisplayName("DADO um aluno" +
                 "QUANDO for executado o buscar no banco " +
                 "ENTÃO o aluno deve ser retornado sem erros")
    void test3() {
        AlunoEntity alunoEntity = new AlunoEntity(UUID.randomUUID().toString(), "teste", "BASICO", 1, new ArrayList<>(), new HashMap<>());
        when(alunoRepository.findById(Mockito.any())).thenReturn(Optional.of(alunoEntity));

        Aluno alunoReturned = alunoGateway.procurarAlunoPorId("id");

        assertEquals(alunoReturned.getId().toString(), alunoEntity.getId());

        verify(alunoRepository).findById("id");
    }

    @Test
    @DisplayName("DADO um aluno" +
                 "QUANDO for executado o buscar no banco e for dado um erro ao buscar na base" +
                 "ENTÃO deve ser lançado um DataBaseComunicationException")
    void test4() {
        when(alunoRepository.findById(Mockito.any())).thenThrow(InvalidJpaQueryMethodException.class);

        assertThrows(DataBaseComunicationException.class, () -> alunoGateway.procurarAlunoPorId("id"));

        verify(alunoRepository).findById("id");
    }
}