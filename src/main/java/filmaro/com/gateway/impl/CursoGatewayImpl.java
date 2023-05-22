package filmaro.com.gateway.impl;

import filmaro.com.domain.Curso;
import filmaro.com.exception.DataBaseComunicationException;
import filmaro.com.gateway.CursoGateway;
import filmaro.com.gateway.repository.CursoRepository;
import filmaro.com.gateway.repository.entity.CursoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CursoGatewayImpl implements CursoGateway {

    private final CursoRepository cursoRepository;

    @Override
    public Curso salvarCurso(Curso curso) {
        try {
            cursoRepository.save(curso.convertToEntity());
            return curso;
        } catch (Exception e) {
            throw new DataBaseComunicationException(String.format("Erro ao salvar curso %s na base", curso.getNome()), e);
        }
    }

    @Override
    public Curso procurarCursoPorId(String id) {
        try {
            return cursoRepository.findById(id).map(CursoEntity::convertToCurso).orElse(null);
        } catch (Exception e) {
            throw new DataBaseComunicationException(String.format("Erro ao buscar aluno por id %s na base", id), e);
        }
    }
}
