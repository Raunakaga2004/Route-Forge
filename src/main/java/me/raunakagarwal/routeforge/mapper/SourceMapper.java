package me.raunakagarwal.routeforge.mapper;

import me.raunakagarwal.routeforge.dto.SourceCreateRequestDTO;
import me.raunakagarwal.routeforge.dto.SourceResponseDTO;
import me.raunakagarwal.routeforge.entity.Source;
import org.springframework.stereotype.Component;

@Component
public class SourceMapper {
    public Source toEntity(SourceCreateRequestDTO sourceRequestDTO) {
        Source source = new Source();
        source.setDomain(sourceRequestDTO.getDomain());
        source.setPort(sourceRequestDTO.getPort());
        return source;
    }

    public SourceResponseDTO toDTO(Source source) {
        SourceResponseDTO sourceResponseDTO = new SourceResponseDTO();
        sourceResponseDTO.setId(source.getId());
        sourceResponseDTO.setDomain(source.getDomain());
        sourceResponseDTO.setPort(source.getPort());
        return sourceResponseDTO;
    }
}