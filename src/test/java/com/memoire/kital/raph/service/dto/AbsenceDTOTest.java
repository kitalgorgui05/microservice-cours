package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class AbsenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbsenceDTO.class);
        AbsenceDTO absenceDTO1 = new AbsenceDTO();
        absenceDTO1.setId(null);
        AbsenceDTO absenceDTO2 = new AbsenceDTO();
        assertThat(absenceDTO1).isNotEqualTo(absenceDTO2);
        absenceDTO2.setId(absenceDTO1.getId());
        assertThat(absenceDTO1).isEqualTo(absenceDTO2);
        absenceDTO2.setId(null);
        assertThat(absenceDTO1).isNotEqualTo(absenceDTO2);
        absenceDTO1.setId(null);
        assertThat(absenceDTO1).isNotEqualTo(absenceDTO2);
    }
}
