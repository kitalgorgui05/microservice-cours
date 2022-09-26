package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Cours;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoursRepository extends JpaRepository<Cours, String>, JpaSpecificationExecutor<Cours> {
}
