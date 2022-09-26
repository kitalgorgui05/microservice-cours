package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HoraireMapperTest {

    private HoraireMapper horaireMapper;

    @BeforeEach
    public void setUp() {
        horaireMapper = new HoraireMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = null;
        assertThat(horaireMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(horaireMapper.fromId(null)).isNull();
    }
}
