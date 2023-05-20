package filmaro.com.gateway.impl;

import filmaro.com.domain.Aluno;
import filmaro.com.exception.DataBaseComunicationException;
import filmaro.com.gateway.AlunoGateway;
import filmaro.com.gateway.repository.AlunoRepository;
import filmaro.com.gateway.repository.entity.AlunoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlunoGatewayImpl implements AlunoGateway {

    private final AlunoRepository alunoRepository;

    @Override
    public Aluno salvarAluno(Aluno aluno) {
        try {
            alunoRepository.save(aluno.convertToEntity());
            return aluno;
        } catch (Exception e) {
            throw new DataBaseComunicationException(String.format("Erro ao salvar aluno %s na base", aluno.getNome()), e);
        }
    }

    @Override
    public Aluno procurarAlunoPorId(String id) {
        try {
            return alunoRepository.findById(id).map(AlunoEntity::convertToAluno).orElse(null);
        } catch (Exception e) {
            throw new DataBaseComunicationException(String.format("Erro ao buscar aluno por id %s na base", id), e);
        }
    }
}
