package com.memoire.kital.raph.openFeign;

import com.memoire.kital.raph.restClient.EleveClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${jhipster.clientApp.name}" , url = "http://localhost:8888")
public interface IEleveRestClient {
    @GetMapping("/api/eleves")
    ResponseEntity<List<EleveClient>> getAllEleves();

    @GetMapping("/api/eleves/{id}")
    ResponseEntity<EleveClient> getEleve(@PathVariable("id") String id);
}
