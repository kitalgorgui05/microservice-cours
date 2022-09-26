package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.HoraireDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Horaire}.
 */
public interface HoraireService {

    /**
     * Save a horaire.
     *
     * @param horaireDTO the entity to save.
     * @return the persisted entity.
     */
    HoraireDTO save(HoraireDTO horaireDTO);

    /**
     * Get all the horaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HoraireDTO> findAll(Pageable pageable);


    /**
     * Get the "id" horaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HoraireDTO> findOne(String id);

    /**
     * Delete the "id" horaire.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
