package com.memoire.kital.raph.service;

import com.memoire.kital.raph.restClient.*;
import com.memoire.kital.raph.service.dto.CoursDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Cours}.
 */
public interface CoursService {

    CoursDTO save(CoursDTO coursDTO);
    Page<CoursDTO> findAll(Pageable pageable);


    List<NiveauClient> getAllNiveau();
    ResponseEntity<List<AnneeClient>> getAllAnnee();
   ResponseEntity<List<ClasseClient>> getAllClasse();
    ResponseEntity<List<MatiereClient>> getAllMatiere();
    Optional<CoursDTO> findOne(String id);
    void delete(String id);
}
