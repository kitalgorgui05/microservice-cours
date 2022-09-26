package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class CoursDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoursDTO.class);
        CoursDTO coursDTO1 = new CoursDTO();
        coursDTO1.setId(null);
        CoursDTO coursDTO2 = new CoursDTO();
        assertThat(coursDTO1).isNotEqualTo(coursDTO2);
        coursDTO2.setId(coursDTO1.getId());
        assertThat(coursDTO1).isEqualTo(coursDTO2);
        coursDTO2.setId(null);
        assertThat(coursDTO1).isNotEqualTo(coursDTO2);
        coursDTO1.setId(null);
        assertThat(coursDTO1).isNotEqualTo(coursDTO2);
    }
}
