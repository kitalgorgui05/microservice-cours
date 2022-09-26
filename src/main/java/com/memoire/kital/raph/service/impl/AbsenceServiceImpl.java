package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.AbsenceService;
import com.memoire.kital.raph.domain.Absence;
import com.memoire.kital.raph.repository.AbsenceRepository;
import com.memoire.kital.raph.service.dto.AbsenceDTO;
import com.memoire.kital.raph.service.mapper.AbsenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Absence}.
 */
@Service
@Transactional
public class AbsenceServiceImpl implements AbsenceService {

    private final Logger log = LoggerFactory.getLogger(AbsenceServiceImpl.class);

    private final AbsenceRepository absenceRepository;

    private final AbsenceMapper absenceMapper;

    public AbsenceServiceImpl(AbsenceRepository absenceRepository, AbsenceMapper absenceMapper) {
        this.absenceRepository = absenceRepository;
        this.absenceMapper = absenceMapper;
    }

    @Override
    public AbsenceDTO save(AbsenceDTO absenceDTO) {
        log.debug("Request to save Absence : {}", absenceDTO);
        Absence absence = absenceMapper.toEntity(absenceDTO);
        absence = absenceRepository.save(absence);
        return absenceMapper.toDto(absence);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AbsenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Absences");
        return absenceRepository.findAll(pageable)
            .map(absenceMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AbsenceDTO> findOne(String id) {
        log.debug("Request to get Absence : {}", id);
        return absenceRepository.findById(id)
            .map(absenceMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Absence : {}", id);
        absenceRepository.deleteById(id);
    }
}