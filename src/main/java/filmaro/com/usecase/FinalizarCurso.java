package filmaro.com.usecase;

import filmaro.com.host.dto.request.FinalizarCursoRequest;

public interface FinalizarCurso {

    boolean execute(String idAluno, FinalizarCursoRequest finalizarCursoRequest);
}
