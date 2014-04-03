/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru._12kb.mesagelib;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 12kb
 */
public class SpeakerTest {
    
    public SpeakerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of regListener method, of class Speaker.
     */
    @Test
    public void testRegListener() {
        System.out.println("regListener");
        Listener ear = null;
        String word = "";
        Speaker instance = null;
        boolean expResult = false;
        boolean result = instance.regListener(ear, word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rmListener method, of class Speaker.
     */
    @Test
    public void testRmListener() {
        System.out.println("rmListener");
        Listener ear = null;
        String word = "";
        Speaker instance = null;
        boolean expResult = false;
        boolean result = instance.rmListener(ear, word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of say method, of class Speaker.
     */
    @Test
    public void testSay() throws Exception {
        System.out.println("say");
        String word = "";
        Speaker instance = null;
        boolean expResult = false;
        boolean result = instance.say(word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}