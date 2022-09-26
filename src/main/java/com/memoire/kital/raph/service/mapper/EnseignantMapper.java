package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.EnseignantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enseignant} and its DTO {@link EnseignantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnseignantMapper extends EntityMapper<EnseignantDTO, Enseignant> {


    @Mapping(target = "cours", ignore = true)
    @Mapping(target = "removeCours", ignore = true)
    Enseignant toEntity(EnseignantDTO enseignantDTO);

    default Enseignant fromId(String id) {
        if (id == null) {
            return null;
        }
        Enseignant enseignant = new Enseignant();
        enseignant.setId(id);
        return enseignant;
    }
}
