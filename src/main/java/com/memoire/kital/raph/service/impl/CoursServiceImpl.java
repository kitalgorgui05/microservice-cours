package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.openFeign.AnneRestClient;
import com.memoire.kital.raph.openFeign.ClasseRestClient;
import com.memoire.kital.raph.openFeign.MatiereRestClient;
import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.MatiereClient;
import com.memoire.kital.raph.service.CoursService;
import com.memoire.kital.raph.domain.Cours;
import com.memoire.kital.raph.repository.CoursRepository;
import com.memoire.kital.raph.service.dto.CoursDTO;
import com.memoire.kital.raph.service.mapper.CoursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cours}.
 */
@Service
@Transactional
public class CoursServiceImpl implements CoursService {

    private final Logger log = LoggerFactory.getLogger(CoursServiceImpl.class);

    private final CoursRepository coursRepository;

    private final CoursMapper coursMapper;

    private final ClasseRestClient classeRestClient;

    private final MatiereRestClient matiereRestClient;

    private final AnneRestClient anneRestClient;

    public CoursServiceImpl(CoursRepository coursRepository, CoursMapper coursMapper, ClasseRestClient classeRestClient, MatiereRestClient matiereRestClient, AnneRestClient anneRestClient) {
        this.coursRepository = coursRepository;
        this.coursMapper = coursMapper;
        this.classeRestClient = classeRestClient;
        this.matiereRestClient = matiereRestClient;
        this.anneRestClient = anneRestClient;
    }

    @Override
    public CoursDTO save(CoursDTO coursDTO) {
        log.debug("Request to save Cours : {}", coursDTO);
        Cours cours = coursMapper.toEntity(coursDTO);
        cours = coursRepository.save(cours);
        return coursMapper.toDto(cours);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CoursDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cours");
        return coursRepository.findAll(pageable)
            .map(coursMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CoursDTO> findOne(String id) {
/*        log.debug("Request to get Cours : {}", id);
        return coursRepository.findById(id)
            .map(coursMapper::toDto);*/
        Cours cours= coursRepository.findById(id).get();
        ClasseClient classeClient= classeRestClient.getClasse(cours.getIdClasse()).getBody();
        MatiereClient matiereClient=matiereRestClient.getMatiere(cours.getIdMatiere()).getBody();
        AnneeClient anneeClient = anneRestClient.getAnnee(cours.getIdAnnee()).getBody();
        cours.setClasseClient(classeClient);
        cours.setMatiereClient(matiereClient);
        cours.setAnneeClient(anneeClient);
        return Optional.ofNullable(coursMapper.toDto(cours));
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Cours : {}", id);
        coursRepository.deleteById(id);
    }
}
