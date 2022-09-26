package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class CoursTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cours.class);
        Cours cours1 = new Cours();
        cours1.setId(null);
        Cours cours2 = new Cours();
        cours2.setId(cours1.getId());
        assertThat(cours1).isEqualTo(cours2);
        cours2.setId(null);
        assertThat(cours1).isNotEqualTo(cours2);
        cours1.setId(null);
        assertThat(cours1).isNotEqualTo(cours2);
    }
}
