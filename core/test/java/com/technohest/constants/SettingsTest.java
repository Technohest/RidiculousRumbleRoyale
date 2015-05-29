package com.technohest.constants;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oskar on 2015-05-29.
 */
public class SettingsTest {
    @Test
    public void testSettings() throws Exception {
        Settings settings = new Settings();
    }

    @Test
    public void testGetWidth() throws Exception {
        Settings settings = new Settings();
        assertNotNull(settings.getWidth());
    }

    @Test
    public void testGetHeight() throws Exception {
        Settings settings = new Settings();
        assertNotNull(settings.getHeight());
    }
}