package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.AbsenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Absence} and its DTO {@link AbsenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CoursMapper.class})
public interface AbsenceMapper extends EntityMapper<AbsenceDTO, Absence> {

    @Mapping(source = "cours.id", target = "coursId")
    AbsenceDTO toDto(Absence absence);

    @Mapping(source = "coursId", target = "cours")
    Absence toEntity(AbsenceDTO absenceDTO);

    default Absence fromId(String id) {
        if (id == null) {
            return null;
        }
        Absence absence = new Absence();
        absence.setId(id);
        return absence;
    }
}
