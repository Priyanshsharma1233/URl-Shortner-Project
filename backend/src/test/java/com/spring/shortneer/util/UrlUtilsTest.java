package com.spring.shortneer.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UrlUtilsTest {

    private UrlUtils urlUtils = new UrlUtils();

    @Test
    void Test_isValid(){
        assertFalse(urlUtils.isValid("spring prject "));
        assertTrue(urlUtils.isValid("https://facebook.com"));
        assertTrue(urlUtils.isValid("http://facebook.com"));
        assertFalse(urlUtils.isValid("htt://facebook.com"));
        assertFalse(urlUtils.isValid("//facebook.com"));
    }



}
