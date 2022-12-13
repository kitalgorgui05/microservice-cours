package com.memoire.kital.raph.restClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EleveClient {
    private String id;
    @NotNull
    @Size(min = 2, max = 30)
    private String prenom;

    @NotNull
    @Size(min = 2, max = 30)
    private String nom;
}
