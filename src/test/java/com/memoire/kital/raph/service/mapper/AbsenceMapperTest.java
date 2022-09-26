package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AbsenceMapperTest {

    private AbsenceMapper absenceMapper;

    @BeforeEach
    public void setUp() {
        absenceMapper = new AbsenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = null;
        assertThat(absenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(absenceMapper.fromId(null)).isNull();
    }
}
