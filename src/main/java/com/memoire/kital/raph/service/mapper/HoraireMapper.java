package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.HoraireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Horaire} and its DTO {@link HoraireDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HoraireMapper extends EntityMapper<HoraireDTO, Horaire> {


    @Mapping(target = "cours", ignore = true)
    @Mapping(target = "removeCours", ignore = true)
    Horaire toEntity(HoraireDTO horaireDTO);

    default Horaire fromId(String id) {
        if (id == null) {
            return null;
        }
        Horaire horaire = new Horaire();
        horaire.setId(id);
        return horaire;
    }
}
