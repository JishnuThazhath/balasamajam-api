package com.balasamajam.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        ReflectionTestUtils.setField(jwtTokenUtil, "jwtSecret", "4F58AE9B3D2CC7C4E6C51D5D8927E4F58AE9B3D2CC7C4E6C51D5D8927E");
    }

    @Test
    public void doGenerateTokenTest() {
        String token = jwtTokenUtil.doGenerateToken("dummy");
        Assertions.assertNotNull(token);
    }
}
