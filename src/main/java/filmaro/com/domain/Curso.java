package filmaro.com.domain;

import filmaro.com.gateway.repository.entity.CursoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Curso {

    private UUID id;
    private String nome;

    public Curso(String nome) {
        this.id = UUID.randomUUID();
        this.nome = nome;
    }

    public CursoEntity convertToEntity() {
        return CursoEntity.builder()
                .id(id.toString())
                .nome(nome)
                .build();
    }
}
