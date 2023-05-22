package filmaro.com.usecase.impl;

import filmaro.com.domain.Aluno;
import filmaro.com.gateway.AlunoGateway;
import filmaro.com.gateway.CursoGateway;
import filmaro.com.host.dto.request.FinalizarCursoRequest;
import filmaro.com.usecase.FinalizarCurso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinalizarCursoImpl implements FinalizarCurso {

    private final AlunoGateway alunoGateway;
    private final CursoGateway cursoGateway;

    @Override
    public boolean execute(String idAluno, FinalizarCursoRequest finalizarCursoRequest) {
        Aluno aluno = alunoGateway.procurarAlunoPorId(idAluno);
        boolean cursoFinalizado = aluno.finalizarCurso(finalizarCursoRequest.criarNota());
        if (cursoFinalizado)
            finalizarCursoRequest.getIdsCursosDesejados().forEach(x -> aluno.addCurso(cursoGateway.procurarCursoPorId(x)));

        alunoGateway.salvarAluno(aluno);
        return cursoFinalizado;
    }
}
