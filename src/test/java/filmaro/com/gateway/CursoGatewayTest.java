package filmaro.com.gateway;

import filmaro.com.domain.Curso;
import filmaro.com.exception.DataBaseComunicationException;
import filmaro.com.gateway.impl.CursoGatewayImpl;
import filmaro.com.gateway.repository.CursoRepository;
import filmaro.com.gateway.repository.entity.CursoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CursoGatewayTest {

    @Mock
    private CursoRepository cursoRepository;

    private CursoGateway cursoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cursoGateway = new CursoGatewayImpl(cursoRepository);
    }

    @Test
    @DisplayName("DADO um curso" +
                 "QUANDO for executado o save no banco " +
                 "ENTÃO o curso deve ser salvo sem erros")
    void test1() {
        Curso curso = new Curso("teste");

        when(cursoRepository.save(Mockito.any())).thenReturn(null);

        Curso cursoReturned = cursoGateway.salvarCurso(curso);

        assertEquals(cursoReturned, curso);

        verify(cursoRepository).save(refEq(curso.convertToEntity()));
    }

    @Test
    @DisplayName("DADO um curso" +
                 "QUANDO for executado o save no banco e for dado um erro ao salvar na base" +
                 "ENTÃO deve ser lançado um DataBaseComunicationException")
    void test2() {
        Curso curso = new Curso("teste");

        when(cursoRepository.save(Mockito.any())).thenThrow(InvalidJpaQueryMethodException.class);

        assertThrows(DataBaseComunicationException.class, () -> cursoGateway.salvarCurso(curso));

        verify(cursoRepository).save(refEq(curso.convertToEntity()));
    }

    @Test
    @DisplayName("DADO um curso" +
                 "QUANDO for executado o buscar no banco " +
                 "ENTÃO o curso deve ser retornado sem erros")
    void test3() {
        CursoEntity cursoEntity = new CursoEntity(UUID.randomUUID().toString(), "teste", new ArrayList<>());
        when(cursoRepository.findById(Mockito.any())).thenReturn(Optional.of(cursoEntity));

        Curso cursoReturned = cursoGateway.procurarCursoPorId("id");

        assertEquals(cursoReturned.getId().toString(), cursoEntity.getId());

        verify(cursoRepository).findById("id");
    }

    @Test
    @DisplayName("DADO um curso" +
                 "QUANDO for executado o buscar no banco e for dado um erro ao buscar na base" +
                 "ENTÃO deve ser lançado um DataBaseComunicationException")
    void test4() {
        when(cursoRepository.findById(Mockito.any())).thenThrow(InvalidJpaQueryMethodException.class);

        assertThrows(DataBaseComunicationException.class, () -> cursoGateway.procurarCursoPorId("id"));

        verify(cursoRepository).findById("id");
    }
}