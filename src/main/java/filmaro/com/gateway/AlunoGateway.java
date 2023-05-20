package filmaro.com.gateway;

import filmaro.com.domain.Aluno;

public interface AlunoGateway {

    Aluno salvarAluno(Aluno aluno);
    Aluno procurarAlunoPorId(String id);
}
