package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.CoursDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Cours}.
 */
public interface CoursService {

    /**
     * Save a cours.
     *
     * @param coursDTO the entity to save.
     * @return the persisted entity.
     */
    CoursDTO save(CoursDTO coursDTO);

    /**
     * Get all the cours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoursDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cours.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoursDTO> findOne(String id);

    /**
     * Delete the "id" cours.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
