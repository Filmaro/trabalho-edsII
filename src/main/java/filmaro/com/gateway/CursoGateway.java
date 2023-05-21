package filmaro.com.gateway;

import filmaro.com.domain.Curso;

public interface CursoGateway {

    Curso salvarCurso(Curso aluno);
    Curso procurarCursoPorId(String id);
}
