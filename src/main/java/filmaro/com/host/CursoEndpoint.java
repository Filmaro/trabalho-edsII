package filmaro.com.host;

import filmaro.com.domain.Curso;
import filmaro.com.gateway.CursoGateway;
import filmaro.com.host.dto.request.CriarCursoRequest;
import filmaro.com.host.handler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/curso")
public class CursoEndpoint {

    private final CursoGateway cursoGateway;

    @Operation(tags = "Curso", description = "Criar curso", responses = {
            @ApiResponse(responseCode = "201", description = "Curso criado na base"),
            @ApiResponse(responseCode = "400", description = "Request inválido", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Endpoint não existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/criar")
    public ResponseEntity<Void> criarCurso(@RequestBody CriarCursoRequest criarAlunoRequest) {
        Curso curso = cursoGateway.salvarCurso(criarAlunoRequest.convertToCurso());
        return ResponseEntity.created(URI.create("/curso/" + curso.getId())).build();
    }

}
