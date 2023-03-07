package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.CoursDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EnseignantMapper.class, HoraireMapper.class})
public interface CoursMapper extends EntityMapper<CoursDTO, Cours> {
    CoursDTO toDto(Cours cours);
    @Mapping(target = "absences", ignore = true)
    @Mapping(target = "removeAbsence", ignore = true)
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
