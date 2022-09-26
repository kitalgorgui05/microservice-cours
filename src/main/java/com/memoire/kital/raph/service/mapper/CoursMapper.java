package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.CoursDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cours} and its DTO {@link CoursDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnseignantMapper.class, HoraireMapper.class})
public interface CoursMapper extends EntityMapper<CoursDTO, Cours> {

    @Mapping(source = "enseignant.id", target = "enseignantId")
    @Mapping(source = "horaire.id", target = "horaireId")
    CoursDTO toDto(Cours cours);

    @Mapping(target = "absences", ignore = true)
    @Mapping(target = "removeAbsence", ignore = true)
    @Mapping(source = "enseignantId", target = "enseignant")
    @Mapping(source = "horaireId", target = "horaire")
    Cours toEntity(CoursDTO coursDTO);

    default Cours fromId(String id) {
        if (id == null) {
            return null;
        }
        Cours cours = new Cours();
        cours.setId(id);
        return cours;
    }
}
