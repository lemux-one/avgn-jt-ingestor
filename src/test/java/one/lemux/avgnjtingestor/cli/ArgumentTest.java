/*
 * Copyright (C) 2022 lemux
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package one.lemux.avgnjtingestor.cli;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lemux
 */
public class ArgumentTest {
    
    public ArgumentTest() {
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
     * Test of getName method, of class Argument.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Argument instance = new Argument("input_file");
        String expResult = "input_file";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Argument.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "field";
        Argument instance = new Argument("wrongName");
        instance.setName(name);
        assertEquals(name, instance.getName());
    }
    
}
