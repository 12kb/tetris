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

import ru._12kb.tetris.TetrisGame.Tetris;
/**
 *
 * @author 12kb
 */
public class ListenerTest {
    
    Listener tstListener;
    
    public ListenerTest() {
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
     * Test of react method, of class Listener.
     */
    @Test
    public void testReact() throws Exception {
        System.out.println("react");
        Listener instance = new Tetris();
        instance.react(word);
        instance.react
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }