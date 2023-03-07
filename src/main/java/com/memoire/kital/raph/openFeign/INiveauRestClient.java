package com.memoire.kital.raph.openFeign;

import com.memoire.kital.raph.restClient.NiveauClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${jhipster.clientApp.name}" , url = "http://localhost:8802")
public interface INiveauRestClient {
    @GetMapping(value = "/api/niveaus")
    List<NiveauClient> getAllNiveaus();
    @GetMapping(value = "/api/niveaus/{id}")
    NiveauClient getNiveau(@PathVariable("id") String id);
}
