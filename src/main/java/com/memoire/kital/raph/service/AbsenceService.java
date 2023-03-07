package com.memoire.kital.raph.service;

import com.memoire.kital.raph.restClient.EleveClient;
import com.memoire.kital.raph.service.dto.AbsenceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AbsenceService {
    AbsenceDTO save(AbsenceDTO absenceDTO);
    Page<AbsenceDTO> findAll(Pageable pageable);
    ResponseEntity<List<EleveClient>> getAllEleve();
    Optional<AbsenceDTO> findOne(String id);
    void delete(String id);
}
