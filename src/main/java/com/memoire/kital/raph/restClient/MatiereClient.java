package com.memoire.kital.raph.restClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatiereClient {
    private String id;
    private String nom;
    private Set<NiveauClient> niveaus = new HashSet<>();

}
