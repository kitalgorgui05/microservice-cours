package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.AbsenceDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CoursMapper.class})
public interface AbsenceMapper extends EntityMapper<AbsenceDTO, Absence> {
    default Absence fromId(String id) {
        if (id == null) {
            return null;
        }
        Absence absence = new Absence();
        absence.setId(id);
        return absence;
    }
}
