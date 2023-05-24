package integrado;

import filmaro.com.Application;
import filmaro.com.domain.enums.TipoAssinaturaEnum;
import filmaro.com.gateway.repository.AlunoRepository;
import filmaro.com.gateway.repository.CursoRepository;
import filmaro.com.gateway.repository.entity.AlunoEntity;
import filmaro.com.gateway.repository.entity.CursoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
public class EndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoRepository alunoRepository;

    @MockBean
    private CursoRepository cursoRepository;

    @Test
    @DisplayName("DADO um CriarAlunoRequest  " +
                 "QUANDO chamado o endpoint de criar o aluno e já houver um curso disponível " +
                 "ENTÃO registrado na base o novo Aluno")
    void test1() throws Exception {
        UUID idCurso = UUID.randomUUID();
        CursoEntity cursoEntity = new CursoEntity(idCurso.toString(), "Inglês", null);
        when(cursoRepository.findById(idCurso.toString())).thenReturn(Optional.of(cursoEntity));
        when(alunoRepository.save(Mockito.any())).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(post("/aluno/criar")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "  \"nome\": \"Eduardo\"," +
                        "  \"idCursoInicial\": \"" + idCurso + "\"" +
                        "}")).andExpect(status().isCreated()).andReturn();

        String alunoId = mvcResult.getResponse().getRedirectedUrl().split("/")[3];
        AlunoEntity alunoExpected = new AlunoEntity(alunoId, "Eduardo", TipoAssinaturaEnum.BASICO.toString(), 0, Collections.singletonList(new CursoEntity(idCurso.toString(), null, null)), null);
        verify(alunoRepository).save(alunoExpected);
    }

    @Test
    @DisplayName("DADO um CriarCursoRequest  " +
                 "QUANDO chamado o endpoint de criar o curso " +
                 "ENTÃO deve ser registrado na base o novo Curso")
    void test2() throws Exception {
        when(cursoRepository.save(Mockito.any())).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(post("/curso/criar")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "  \"nome\": \"Inglês\"" +
                        "}")).andExpect(status().isCreated()).andReturn();

        String cursoId = mvcResult.getResponse().getRedirectedUrl().split("/")[2];
        CursoEntity cursoEntity = new CursoEntity(cursoId, "Inglês", null);
        verify(cursoRepository).save(cursoEntity);
    }

    @Test
    @DisplayName("DADO um FinalizarCursoRequest e um alunoId  " +
                 "QUANDO chamado o endpoint de finalizarCurso " +
                 "ENTÃO deve ser feita a regra e retornado 204")
    void test3() throws Exception {
        CursoEntity cursoFinalizado = new CursoEntity(UUID.randomUUID().toString(), "Inglês", null);
        CursoEntity cursoDesejado = new CursoEntity(UUID.randomUUID().toString(), "Física", null);
        AlunoEntity alunoEntity = new AlunoEntity(UUID.randomUUID().toString(), "Frate", TipoAssinaturaEnum.BASICO.toString(), 0, new ArrayList<>(), null);
        alunoEntity.getCursosAdicionados().add(cursoFinalizado);

        when(alunoRepository.findById(Mockito.any())).thenReturn(Optional.of(alunoEntity));
        when(cursoRepository.findById(cursoDesejado.getId())).thenReturn(Optional.of(cursoDesejado));
        when(alunoRepository.save(Mockito.any())).thenReturn(null);

        mockMvc.perform(post("/aluno/finalizar-curso/" + alunoEntity.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"idCursoFinalizado\": \"" + cursoFinalizado.getId() + "\",\n" +
                        "  \"notaCurso\": 10,\n" +
                        "  \"idsCursosDesejados\": [\n" +
                        "    \"" + cursoDesejado.getId() + "\"\n" +
                        "  ]\n" +
                        "}")).andExpect(status().isNoContent());

        verify(alunoRepository).findById(alunoEntity.getId());
        verify(cursoRepository).findById(cursoDesejado.getId());

        ArgumentCaptor<AlunoEntity> listCaptor = ArgumentCaptor.forClass(AlunoEntity.class);
        verify(alunoRepository).save(listCaptor.capture());
        AlunoEntity realAluno = listCaptor.getValue();

        assertTrue(realAluno.getCursosAdicionados().contains(cursoDesejado));
        assertTrue(realAluno.getCursosAdicionados().contains(cursoFinalizado));

        alunoEntity.setQtdCursosDisponiveis(2);
        alunoEntity.setCursosAdicionados(null);
        realAluno.setCursosAdicionados(null);
        assertEquals(alunoEntity, realAluno);
    }
}
