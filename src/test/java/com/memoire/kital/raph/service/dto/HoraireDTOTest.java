package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class HoraireDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoraireDTO.class);
        HoraireDTO horaireDTO1 = new HoraireDTO();
        horaireDTO1.setId(null);
        HoraireDTO horaireDTO2 = new HoraireDTO();
        assertThat(horaireDTO1).isNotEqualTo(horaireDTO2);
        horaireDTO2.setId(horaireDTO1.getId());
        assertThat(horaireDTO1).isEqualTo(horaireDTO2);
        horaireDTO2.setId(null);
        assertThat(horaireDTO1).isNotEqualTo(horaireDTO2);
        horaireDTO1.setId(null);
        assertThat(horaireDTO1).isNotEqualTo(horaireDTO2);
    }
}
