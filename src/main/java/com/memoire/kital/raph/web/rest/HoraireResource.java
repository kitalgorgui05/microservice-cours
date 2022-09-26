package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.HoraireService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.HoraireDTO;
import com.memoire.kital.raph.service.dto.HoraireCriteria;
import com.memoire.kital.raph.service.HoraireQueryService;

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
 * REST controller for managing {@link com.memoire.kital.raph.domain.Horaire}.
 */
@RestController
@RequestMapping("/api")
public class HoraireResource {

    private final Logger log = LoggerFactory.getLogger(HoraireResource.class);

    private static final String ENTITY_NAME = "coursHoraire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoraireService horaireService;

    private final HoraireQueryService horaireQueryService;

    public HoraireResource(HoraireService horaireService, HoraireQueryService horaireQueryService) {
        this.horaireService = horaireService;
        this.horaireQueryService = horaireQueryService;
    }

    /**
     * {@code POST  /horaires} : Create a new horaire.
     *
     * @param horaireDTO the horaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horaireDTO, or with status {@code 400 (Bad Request)} if the horaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/horaires")
    public ResponseEntity<HoraireDTO> createHoraire(@Valid @RequestBody HoraireDTO horaireDTO) throws URISyntaxException {
        log.debug("REST request to save Horaire : {}", horaireDTO);
        if (horaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new horaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoraireDTO result = horaireService.save(horaireDTO);
        return ResponseEntity.created(new URI("/api/horaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /horaires} : Updates an existing horaire.
     *
     * @param horaireDTO the horaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horaireDTO,
     * or with status {@code 400 (Bad Request)} if the horaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/horaires")
    public ResponseEntity<HoraireDTO> updateHoraire(@Valid @RequestBody HoraireDTO horaireDTO) throws URISyntaxException {
        log.debug("REST request to update Horaire : {}", horaireDTO);
        if (horaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HoraireDTO result = horaireService.save(horaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, horaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /horaires} : get all the horaires.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horaires in body.
     */
    @GetMapping("/horaires")
    public ResponseEntity<List<HoraireDTO>> getAllHoraires(HoraireCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Horaires by criteria: {}", criteria);
        Page<HoraireDTO> page = horaireQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /horaires/count} : count all the horaires.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/horaires/count")
    public ResponseEntity<Long> countHoraires(HoraireCriteria criteria) {
        log.debug("REST request to count Horaires by criteria: {}", criteria);
        return ResponseEntity.ok().body(horaireQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /horaires/:id} : get the "id" horaire.
     *
     * @param id the id of the horaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/horaires/{id}")
    public ResponseEntity<HoraireDTO> getHoraire(@PathVariable String id) {
        log.debug("REST request to get Horaire : {}", id);
        Optional<HoraireDTO> horaireDTO = horaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horaireDTO);
    }

    /**
     * {@code DELETE  /horaires/:id} : delete the "id" horaire.
     *
     * @param id the id of the horaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/horaires/{id}")
    public ResponseEntity<Void> deleteHoraire(@PathVariable String id) {
        log.debug("REST request to delete Horaire : {}", id);
        horaireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
