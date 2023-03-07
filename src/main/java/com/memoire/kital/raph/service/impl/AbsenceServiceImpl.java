package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.domain.Cours;
import com.memoire.kital.raph.openFeign.IAnneRestClient;
import com.memoire.kital.raph.openFeign.IClasseRestClient;
import com.memoire.kital.raph.openFeign.IEleveRestClient;
import com.memoire.kital.raph.openFeign.IMatiereRestClient;
import com.memoire.kital.raph.repository.CoursRepository;
import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.EleveClient;
import com.memoire.kital.raph.restClient.MatiereClient;
import com.memoire.kital.raph.service.AbsenceService;
import com.memoire.kital.raph.domain.Absence;
import com.memoire.kital.raph.repository.AbsenceRepository;
import com.memoire.kital.raph.service.dto.AbsenceDTO;
import com.memoire.kital.raph.service.mapper.AbsenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final IEleveRestClient iEleveRestClient;

    private final CoursRepository coursRepository;
    private final IClasseRestClient iClasseRestClient;
    private final IMatiereRestClient iMatiereRestClient;
    private final IAnneRestClient iAnneRestClient;

    public AbsenceServiceImpl(AbsenceRepository absenceRepository, AbsenceMapper absenceMapper, IEleveRestClient iEleveRestClient, CoursRepository coursRepository, IClasseRestClient iClasseRestClient, IMatiereRestClient iMatiereRestClient, IAnneRestClient iAnneRestClient) {
        this.absenceRepository = absenceRepository;
        this.absenceMapper = absenceMapper;
        this.iEleveRestClient = iEleveRestClient;
        this.coursRepository = coursRepository;
        this.iClasseRestClient = iClasseRestClient;
        this.iMatiereRestClient = iMatiereRestClient;
        this.iAnneRestClient = iAnneRestClient;
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
    public ResponseEntity<List<EleveClient>> getAllEleve() {
        return iEleveRestClient.getAllEleves();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AbsenceDTO> findOne(String id) {
        Absence absence = absenceRepository.findById(id).orElse(null);
        EleveClient eleveClient=iEleveRestClient.getEleve(absence.getIdEleve()).getBody();
        Cours cours=coursRepository.findById(absence.getCours().getId()).get();
        MatiereClient matiereClient=iMatiereRestClient.getMatiere(cours.getIdMatiere()).getBody();
        ClasseClient classeClient=iClasseRestClient.getClasse(cours.getIdClasse()).getBody();
        AnneeClient anneeClient=iAnneRestClient.getAnnee(cours.getIdAnnee()).getBody();
        cours.setMatiereClient(matiereClient);
        cours.setClasseClient(classeClient);
        cours.setAnneeClient(anneeClient);
        absence.setEleveClient(eleveClient);
        absence.setCours(cours);
        return Optional.ofNullable(absenceMapper.toDto(absence));
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Absence : {}", id);
        absenceRepository.deleteById(id);
    }
}
