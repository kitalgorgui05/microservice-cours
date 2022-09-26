package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.HoraireService;
import com.memoire.kital.raph.domain.Horaire;
import com.memoire.kital.raph.repository.HoraireRepository;
import com.memoire.kital.raph.service.dto.HoraireDTO;
import com.memoire.kital.raph.service.mapper.HoraireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Horaire}.
 */
@Service
@Transactional
public class HoraireServiceImpl implements HoraireService {

    private final Logger log = LoggerFactory.getLogger(HoraireServiceImpl.class);

    private final HoraireRepository horaireRepository;

    private final HoraireMapper horaireMapper;

    public HoraireServiceImpl(HoraireRepository horaireRepository, HoraireMapper horaireMapper) {
        this.horaireRepository = horaireRepository;
        this.horaireMapper = horaireMapper;
    }

    @Override
    public HoraireDTO save(HoraireDTO horaireDTO) {
        log.debug("Request to save Horaire : {}", horaireDTO);
        Horaire horaire = horaireMapper.toEntity(horaireDTO);
        horaire = horaireRepository.save(horaire);
        return horaireMapper.toDto(horaire);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HoraireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Horaires");
        return horaireRepository.findAll(pageable)
            .map(horaireMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<HoraireDTO> findOne(String id) {
        log.debug("Request to get Horaire : {}", id);
        return horaireRepository.findById(id)
            .map(horaireMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Horaire : {}", id);
        horaireRepository.deleteById(id);
    }
}
