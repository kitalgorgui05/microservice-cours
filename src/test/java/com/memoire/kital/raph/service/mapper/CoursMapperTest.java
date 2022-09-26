package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CoursMapperTest {

    private CoursMapper coursMapper;

    @BeforeEach
    public void setUp() {
        coursMapper = new CoursMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = null;
        assertThat(coursMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(coursMapper.fromId(null)).isNull();
    }
}
