package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.restClient.*;
import com.memoire.kital.raph.service.CoursService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.CoursDTO;
import com.memoire.kital.raph.service.dto.CoursCriteria;
import com.memoire.kital.raph.service.CoursQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.memoire.kital.raph.domain.Cours}.
 */
@RestController
@RequestMapping("/api")
public class CoursResource {

    private final Logger log = LoggerFactory.getLogger(CoursResource.class);

    private static final String ENTITY_NAME = "coursCours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoursService coursService;

    private final CoursQueryService coursQueryService;

    public CoursResource(CoursService coursService, CoursQueryService coursQueryService) {
        this.coursService = coursService;
        this.coursQueryService = coursQueryService;
    }

    @PostMapping("/cours")
    public ResponseEntity<CoursDTO> createCours(@Valid @RequestBody CoursDTO coursDTO) throws URISyntaxException {
        log.debug("REST request to save Cours : {}", coursDTO);
        if (coursDTO.getId() != null) {
            throw new BadRequestAlertException("A new cours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoursDTO result = coursService.save(coursDTO);
        return ResponseEntity.created(new URI("/api/cours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/cours")
    public ResponseEntity<CoursDTO> updateCours(@Valid @RequestBody CoursDTO coursDTO) throws URISyntaxException {
        log.debug("REST request to update Cours : {}", coursDTO);
        if (coursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoursDTO result = coursService.save(coursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, coursDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/cours")
    public ResponseEntity<List<CoursDTO>> getAllCours(CoursCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cours by criteria: {}", criteria);
        Page<CoursDTO> page = coursQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/cours/count")
    public ResponseEntity<Long> countCours(CoursCriteria criteria) {
        log.debug("REST request to count Cours by criteria: {}", criteria);
        return ResponseEntity.ok().body(coursQueryService.countByCriteria(criteria));
    }

    @GetMapping("/cours/{id}")
    public ResponseEntity<CoursDTO> getCours(@PathVariable String id) {
        log.debug("REST request to get Cours : {}", id);
        Optional<CoursDTO> coursDTO = coursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coursDTO);
    }

    @GetMapping("/cours/niveaus")
    public List<NiveauClient> getAllNiveau(){
        return coursService.getAllNiveau();
    }
    @GetMapping("/cours/classes")
    public ResponseEntity<List<ClasseClient>> getClasses(){
        return coursService.getAllClasse();
    }

    @GetMapping("/cours/matieres")
    public ResponseEntity<List<MatiereClient>> getMatieres(){
        return coursService.getAllMatiere();
    }
    @GetMapping("/cours/annees")
    public ResponseEntity<List<AnneeClient>> getAnnees(){
        return coursService.getAllAnnee();
    }
    @DeleteMapping("/cours/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable String id) {
        log.debug("REST request to delete Cours : {}", id);
        coursService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
