package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Enseignant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Enseignant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, String>, JpaSpecificationExecutor<Enseignant> {
}
