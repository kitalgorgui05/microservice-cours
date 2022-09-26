package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.EnseignantService;
import com.memoire.kital.raph.domain.Enseignant;
import com.memoire.kital.raph.repository.EnseignantRepository;
import com.memoire.kital.raph.service.dto.EnseignantDTO;
import com.memoire.kital.raph.service.mapper.EnseignantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Enseignant}.
 */
@Service
@Transactional
public class EnseignantServiceImpl implements EnseignantService {

    private final Logger log = LoggerFactory.getLogger(EnseignantServiceImpl.class);

    private final EnseignantRepository enseignantRepository;

    private final EnseignantMapper enseignantMapper;

    public EnseignantServiceImpl(EnseignantRepository enseignantRepository, EnseignantMapper enseignantMapper) {
        this.enseignantRepository = enseignantRepository;
        this.enseignantMapper = enseignantMapper;
    }

    @Override
    public EnseignantDTO save(EnseignantDTO enseignantDTO) {
        log.debug("Request to save Enseignant : {}", enseignantDTO);
        Enseignant enseignant = enseignantMapper.toEntity(enseignantDTO);
        enseignant = enseignantRepository.save(enseignant);
        return enseignantMapper.toDto(enseignant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnseignantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enseignants");
        return enseignantRepository.findAll(pageable)
            .map(enseignantMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EnseignantDTO> findOne(String id) {
        log.debug("Request to get Enseignant : {}", id);
        return enseignantRepository.findById(id)
            .map(enseignantMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Enseignant : {}", id);
        enseignantRepository.deleteById(id);
    }
}
