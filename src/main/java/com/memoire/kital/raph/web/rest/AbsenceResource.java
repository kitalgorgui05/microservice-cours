package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.AbsenceService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.AbsenceDTO;
import com.memoire.kital.raph.service.dto.AbsenceCriteria;
import com.memoire.kital.raph.service.AbsenceQueryService;

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
 * REST controller for managing {@link com.memoire.kital.raph.domain.Absence}.
 */
@RestController
@RequestMapping("/api")
public class AbsenceResource {

    private final Logger log = LoggerFactory.getLogger(AbsenceResource.class);

    private static final String ENTITY_NAME = "coursAbsence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbsenceService absenceService;

    private final AbsenceQueryService absenceQueryService;

    public AbsenceResource(AbsenceService absenceService, AbsenceQueryService absenceQueryService) {
        this.absenceService = absenceService;
        this.absenceQueryService = absenceQueryService;
    }

    /**
     * {@code POST  /absences} : Create a new absence.
     *
     * @param absenceDTO the absenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new absenceDTO, or with status {@code 400 (Bad Request)} if the absence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/absences")
    public ResponseEntity<AbsenceDTO> createAbsence(@Valid @RequestBody AbsenceDTO absenceDTO) throws URISyntaxException {
        log.debug("REST request to save Absence : {}", absenceDTO);
        if (absenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new absence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AbsenceDTO result = absenceService.save(absenceDTO);
        return ResponseEntity.created(new URI("/api/absences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /absences} : Updates an existing absence.
     *
     * @param absenceDTO the absenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated absenceDTO,
     * or with status {@code 400 (Bad Request)} if the absenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the absenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/absences")
    public ResponseEntity<AbsenceDTO> updateAbsence(@Valid @RequestBody AbsenceDTO absenceDTO) throws URISyntaxException {
        log.debug("REST request to update Absence : {}", absenceDTO);
        if (absenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AbsenceDTO result = absenceService.save(absenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, absenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /absences} : get all the absences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of absences in body.
     */
    @GetMapping("/absences")
    public ResponseEntity<List<AbsenceDTO>> getAllAbsences(AbsenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Absences by criteria: {}", criteria);
        Page<AbsenceDTO> page = absenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /absences/count} : count all the absences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/absences/count")
    public ResponseEntity<Long> countAbsences(AbsenceCriteria criteria) {
        log.debug("REST request to count Absences by criteria: {}", criteria);
        return ResponseEntity.ok().body(absenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /absences/:id} : get the "id" absence.
     *
     * @param id the id of the absenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the absenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/absences/{id}")
    public ResponseEntity<AbsenceDTO> getAbsence(@PathVariable String id) {
        log.debug("REST request to get Absence : {}", id);
        Optional<AbsenceDTO> absenceDTO = absenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(absenceDTO);
    }

    /**
     * {@code DELETE  /absences/:id} : delete the "id" absence.
     *
     * @param id the id of the absenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/absences/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable String id) {
        log.debug("REST request to delete Absence : {}", id);
        absenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
