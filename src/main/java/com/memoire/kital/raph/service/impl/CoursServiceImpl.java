package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.openFeign.*;
import com.memoire.kital.raph.restClient.*;
import com.memoire.kital.raph.service.CoursService;
import com.memoire.kital.raph.domain.Cours;
import com.memoire.kital.raph.repository.CoursRepository;
import com.memoire.kital.raph.service.dto.CoursDTO;
import com.memoire.kital.raph.service.mapper.CoursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CoursServiceImpl implements CoursService {

    private final Logger log = LoggerFactory.getLogger(CoursServiceImpl.class);

    private final CoursRepository coursRepository;

    private final CoursMapper coursMapper;

    private final IClasseRestClient iClasseRestClient;

    private final IMatiereRestClient iMatiereRestClient;

    private final IAnneRestClient anneRestClient;

    private final INiveauRestClient iNiveauRestClient;

    public CoursServiceImpl(CoursRepository coursRepository, CoursMapper coursMapper, IClasseRestClient iClasseRestClient, IMatiereRestClient iMatiereRestClient, IAnneRestClient anneRestClient,INiveauRestClient iNiveauRestClient) {
        this.coursRepository = coursRepository;
        this.coursMapper = coursMapper;
        this.iClasseRestClient = iClasseRestClient;
        this.iMatiereRestClient = iMatiereRestClient;
        this.anneRestClient = anneRestClient;
        this.iNiveauRestClient = iNiveauRestClient;
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
    public ResponseEntity<List<AnneeClient>> getAllAnnee() {
        return anneRestClient.getAllAnnees();
    }

    @Override
    public ResponseEntity<List<ClasseClient>> getAllClasse() {
        return iClasseRestClient.getAllClasses();
    }

    @Override
    public ResponseEntity<List<MatiereClient>> getAllMatiere() {
        return iMatiereRestClient.getAllMatieres();
    }
    @Override
    public List<NiveauClient> getAllNiveau() {
        return iNiveauRestClient.getAllNiveaus();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CoursDTO> findOne(String id) {
        Cours cours= coursRepository.findById(id).get();
        ClasseClient classeClient= iClasseRestClient.getClasse(cours.getIdClasse()).getBody();
        MatiereClient matiereClient=iMatiereRestClient.getMatiere(cours.getIdMatiere()).getBody();
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
