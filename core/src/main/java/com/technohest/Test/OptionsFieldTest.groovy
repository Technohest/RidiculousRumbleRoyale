package com.technohest.Test

import com.technohest.core.menu.OptionsField
import org.apache.commons.collections4.BidiMap
import org.apache.commons.collections4.bidimap.DualHashBidiMap
import org.junit.Before
import org.junit.Test

/**
 * Created by Oscar on 2015-05-19.
 */
class OptionsFieldTest extends GroovyTestCase {
    private OptionsField optionsField
    @Before
    public void initialize(){

    }
    @Test
    void testSwitchTo() {
        BidiMap<Integer, String> map;
        map = new DualHashBidiMap<Integer, String>();
        map.put(1,"firstvalue");
        map.put(2,"secondvalue");
        optionsField = new OptionsField("testText" , map);
        int firstValue = optionsField.getCurrent();
        optionsField.switchTo("back");
        int secondValue = optionsField.getCurrent();
        assertTrue(firstValue != secondValue);
        optionsField.switchTo("back");
        secondValue = optionsField.getCurrent();
        assertTrue(firstValue == secondValue);
    }

    @Test
    void testUpdate() {

    }

    @Test
    void testGetCurrent() {

    }
}
