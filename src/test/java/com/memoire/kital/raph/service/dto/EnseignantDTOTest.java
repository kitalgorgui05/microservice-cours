package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class EnseignantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnseignantDTO.class);
        EnseignantDTO enseignantDTO1 = new EnseignantDTO();
        enseignantDTO1.setId(null);
        EnseignantDTO enseignantDTO2 = new EnseignantDTO();
        assertThat(enseignantDTO1).isNotEqualTo(enseignantDTO2);
        enseignantDTO2.setId(enseignantDTO1.getId());
        assertThat(enseignantDTO1).isEqualTo(enseignantDTO2);
        enseignantDTO2.setId(null);
        assertThat(enseignantDTO1).isNotEqualTo(enseignantDTO2);
        enseignantDTO1.setId(null);
        assertThat(enseignantDTO1).isNotEqualTo(enseignantDTO2);
    }
}
