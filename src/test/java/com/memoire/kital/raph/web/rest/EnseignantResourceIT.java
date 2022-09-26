package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.CoursApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Enseignant;
import com.memoire.kital.raph.domain.Cours;
import com.memoire.kital.raph.repository.EnseignantRepository;
import com.memoire.kital.raph.service.EnseignantService;
import com.memoire.kital.raph.service.dto.EnseignantDTO;
import com.memoire.kital.raph.service.mapper.EnseignantMapper;
import com.memoire.kital.raph.service.dto.EnseignantCriteria;
import com.memoire.kital.raph.service.EnseignantQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EnseignantResource} REST controller.
 */
@SpringBootTest(classes = { CoursApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EnseignantResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EnseignantMapper enseignantMapper;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private EnseignantQueryService enseignantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnseignantMockMvc;

    private Enseignant enseignant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enseignant createEntity(EntityManager em) {
        Enseignant enseignant = new Enseignant()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL);
        return enseignant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enseignant createUpdatedEntity(EntityManager em) {
        Enseignant enseignant = new Enseignant()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);
        return enseignant;
    }

    @BeforeEach
    public void initTest() {
        enseignant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnseignant() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();
        // Create the Enseignant
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);
        restEnseignantMockMvc.perform(post("/api/enseignants").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isCreated());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate + 1);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEnseignant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEnseignant.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEnseignant.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEnseignant.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createEnseignantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant with an existing ID
        enseignant.setId(null);
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnseignantMockMvc.perform(post("/api/enseignants").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setNom(null);

        // Create the Enseignant, which fails.
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);


        restEnseignantMockMvc.perform(post("/api/enseignants").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setPrenom(null);

        // Create the Enseignant, which fails.
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);


        restEnseignantMockMvc.perform(post("/api/enseignants").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setTelephone(null);

        // Create the Enseignant, which fails.
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);


        restEnseignantMockMvc.perform(post("/api/enseignants").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setEmail(null);

        // Create the Enseignant, which fails.
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);


        restEnseignantMockMvc.perform(post("/api/enseignants").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnseignants() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    public void getEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", enseignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enseignant.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }


    @Test
    @Transactional
    public void getEnseignantsByIdFiltering() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        String id = enseignant.getId();

        defaultEnseignantShouldBeFound("id.equals=" + id);
        defaultEnseignantShouldNotBeFound("id.notEquals=" + id);

        defaultEnseignantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnseignantShouldNotBeFound("id.greaterThan=" + id);

        defaultEnseignantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnseignantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom equals to DEFAULT_NOM
        defaultEnseignantShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the enseignantList where nom equals to UPDATED_NOM
        defaultEnseignantShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom not equals to DEFAULT_NOM
        defaultEnseignantShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the enseignantList where nom not equals to UPDATED_NOM
        defaultEnseignantShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultEnseignantShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the enseignantList where nom equals to UPDATED_NOM
        defaultEnseignantShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom is not null
        defaultEnseignantShouldBeFound("nom.specified=true");

        // Get all the enseignantList where nom is null
        defaultEnseignantShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnseignantsByNomContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom contains DEFAULT_NOM
        defaultEnseignantShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the enseignantList where nom contains UPDATED_NOM
        defaultEnseignantShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom does not contain DEFAULT_NOM
        defaultEnseignantShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the enseignantList where nom does not contain UPDATED_NOM
        defaultEnseignantShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where prenom equals to DEFAULT_PRENOM
        defaultEnseignantShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the enseignantList where prenom equals to UPDATED_PRENOM
        defaultEnseignantShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where prenom not equals to DEFAULT_PRENOM
        defaultEnseignantShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the enseignantList where prenom not equals to UPDATED_PRENOM
        defaultEnseignantShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultEnseignantShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the enseignantList where prenom equals to UPDATED_PRENOM
        defaultEnseignantShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where prenom is not null
        defaultEnseignantShouldBeFound("prenom.specified=true");

        // Get all the enseignantList where prenom is null
        defaultEnseignantShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnseignantsByPrenomContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where prenom contains DEFAULT_PRENOM
        defaultEnseignantShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the enseignantList where prenom contains UPDATED_PRENOM
        defaultEnseignantShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where prenom does not contain DEFAULT_PRENOM
        defaultEnseignantShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the enseignantList where prenom does not contain UPDATED_PRENOM
        defaultEnseignantShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where telephone equals to DEFAULT_TELEPHONE
        defaultEnseignantShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the enseignantList where telephone equals to UPDATED_TELEPHONE
        defaultEnseignantShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where telephone not equals to DEFAULT_TELEPHONE
        defaultEnseignantShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the enseignantList where telephone not equals to UPDATED_TELEPHONE
        defaultEnseignantShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultEnseignantShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the enseignantList where telephone equals to UPDATED_TELEPHONE
        defaultEnseignantShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where telephone is not null
        defaultEnseignantShouldBeFound("telephone.specified=true");

        // Get all the enseignantList where telephone is null
        defaultEnseignantShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnseignantsByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where telephone contains DEFAULT_TELEPHONE
        defaultEnseignantShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the enseignantList where telephone contains UPDATED_TELEPHONE
        defaultEnseignantShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where telephone does not contain DEFAULT_TELEPHONE
        defaultEnseignantShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the enseignantList where telephone does not contain UPDATED_TELEPHONE
        defaultEnseignantShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where email equals to DEFAULT_EMAIL
        defaultEnseignantShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the enseignantList where email equals to UPDATED_EMAIL
        defaultEnseignantShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where email not equals to DEFAULT_EMAIL
        defaultEnseignantShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the enseignantList where email not equals to UPDATED_EMAIL
        defaultEnseignantShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEnseignantShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the enseignantList where email equals to UPDATED_EMAIL
        defaultEnseignantShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where email is not null
        defaultEnseignantShouldBeFound("email.specified=true");

        // Get all the enseignantList where email is null
        defaultEnseignantShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnseignantsByEmailContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where email contains DEFAULT_EMAIL
        defaultEnseignantShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the enseignantList where email contains UPDATED_EMAIL
        defaultEnseignantShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where email does not contain DEFAULT_EMAIL
        defaultEnseignantShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the enseignantList where email does not contain UPDATED_EMAIL
        defaultEnseignantShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByCoursIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);
        Cours cours = CoursResourceIT.createEntity(em);
        em.persist(cours);
        em.flush();
        enseignant.addCours(cours);
        enseignantRepository.saveAndFlush(enseignant);
        String coursId = cours.getId();

        // Get all the enseignantList where cours equals to coursId
        defaultEnseignantShouldBeFound("coursId.equals=" + coursId);

        // Get all the enseignantList where cours equals to coursId + 1
        defaultEnseignantShouldNotBeFound("coursId.equals=" + (coursId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnseignantShouldBeFound(String filter) throws Exception {
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restEnseignantMockMvc.perform(get("/api/enseignants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnseignantShouldNotBeFound(String filter) throws Exception {
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnseignantMockMvc.perform(get("/api/enseignants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEnseignant() throws Exception {
        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Update the enseignant
        Enseignant updatedEnseignant = enseignantRepository.findById(enseignant.getId()).get();
        // Disconnect from session so that the updates on updatedEnseignant are not directly saved in db
        em.detach(updatedEnseignant);
        updatedEnseignant
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(updatedEnseignant);

        restEnseignantMockMvc.perform(put("/api/enseignants").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isOk());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEnseignant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEnseignant.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEnseignant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEnseignant.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingEnseignant() throws Exception {
        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Create the Enseignant
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnseignantMockMvc.perform(put("/api/enseignants").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        int databaseSizeBeforeDelete = enseignantRepository.findAll().size();

        // Delete the enseignant
        restEnseignantMockMvc.perform(delete("/api/enseignants/{id}", enseignant.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
