package filmaro.com.host;

import filmaro.com.domain.Aluno;
import filmaro.com.gateway.AlunoGateway;
import filmaro.com.host.dto.request.CriarAlunoRequest;
import filmaro.com.host.dto.request.FinalizarCursoRequest;
import filmaro.com.host.handler.ErrorResponse;
import filmaro.com.usecase.FinalizarCurso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aluno")
public class AlunoEndpoint {

    private final AlunoGateway alunoGateway;
    private final FinalizarCurso finalizarCurso;

    @Operation(tags = "Aluno", description = "Cria Aluno", responses = {
            @ApiResponse(responseCode = "201", description = "Aluno criado na base"),
            @ApiResponse(responseCode = "400", description = "Request inválido", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Endpoint não existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/criar")
    public ResponseEntity<Void> criarAluno(@RequestBody CriarAlunoRequest criarAlunoRequest) {
        Aluno aluno = alunoGateway.salvarAluno(criarAlunoRequest.convertToAluno());
        return ResponseEntity.created(URI.create("/aluno/finalizar-curso/" + aluno.getId())).build();
    }

    @Operation(tags = "Aluno", description = "Finalizar curso do Aluno", responses = {
            @ApiResponse(responseCode = "204", description = "Curso finalizado com sucesso, novos cursos adicionados"),
            @ApiResponse(responseCode = "400", description = "Request inválido", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Endpoint não existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
            @ApiResponse(responseCode = "418", description = "Aluno não passou no teste")
    })
    @PostMapping("/finalizar-curso/{alunoId}")
    public ResponseEntity<Object> finalizarCurso(@PathVariable String alunoId, @RequestBody FinalizarCursoRequest finalizarCursoRequest) {
        boolean cursoFinalizado = finalizarCurso.execute(alunoId, finalizarCursoRequest);
        return cursoFinalizado ? ResponseEntity.noContent().build() :
                ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(ErrorResponse.builder().userMessage("Aluno não passou").build());
    }
}
