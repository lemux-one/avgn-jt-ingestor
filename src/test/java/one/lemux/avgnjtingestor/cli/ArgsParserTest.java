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

import java.util.Arrays;
import java.util.List;
import one.lemux.avgnjtingestor.exceptions.WrongArgumentsException;
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
public class ArgsParserTest {

    public ArgsParserTest() {
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
     * Test of addArg method, of class ArgsParser.
     */
    @Test
    public void testAddArg() {
        System.out.println("addArg");
        var arg = new Argument("testArgument");
        var result = new ArgsParser("dummyApp").addPositionalArgument(arg);
        assertEquals(arg, result.getPositionalArgumentList().get(0));
    }

    /**
     * Test of parse method, of class ArgsParser.
     */
    @Test
    public void testParse_withThreeRequiredArgumentsAndNoneProvided() {
        System.out.println("parse_withThreeRequiredArgumentsAndNoneProvided");
        String[] args = new String[]{};
        ArgsParser instance = new ArgsParser("dummyApp")
                .addPositionalArgument(new Argument("testArg1"))
                .addPositionalArgument(new Argument("testArg2"))
                .addPositionalArgument(new Argument("testArg3"));
        try {
            instance.parse(args);
            fail("No exception was thrown but s% was expected."
                    .formatted(WrongArgumentsException.class.getName()));
        } catch (WrongArgumentsException ex) {
            assertEquals(instance.NO_ARGS_MESSAGE, ex.getMessage());
        }
    }

    /**
     * Test of parse method, of class ArgsParser.
     */
    @Test
    public void testParse_withThreeRequiredArgumentsAndLessProvided() {
        System.out.println("parse_withThreeRequiredArgumentsAndLessProvided");
        String[] args = new String[]{"arg1", "arg2"};
        ArgsParser instance = new ArgsParser("dummyApp")
                .addPositionalArgument(new Argument("testArg1"))
                .addPositionalArgument(new Argument("testArg2"))
                .addPositionalArgument(new Argument("testArg3"));
        try {
            instance.parse(args);
            fail("No exception was thrown but s% was expected."
                    .formatted(WrongArgumentsException.class.getName()));
        } catch (WrongArgumentsException ex) {
            assertEquals(instance.WRONG_NUMBER_OF_ARGS_MESSAGE, ex.getMessage());
        }
    }

    /**
     * Test of parse method, of class ArgsParser.
     */
    @Test
    public void testParse_withThreeRequiredArgumentsAndMoreProvided() {
        System.out.println("parse_withThreeRequiredArgumentsAndMoreProvided");
        String[] args = new String[]{"arg1", "arg2", "arg3", "arg4"};
        ArgsParser instance = new ArgsParser("dummyApp")
                .addPositionalArgument(new Argument("testArg1"))
                .addPositionalArgument(new Argument("testArg2"))
                .addPositionalArgument(new Argument("testArg3"));
        try {
            instance.parse(args);
            fail("No exception was thrown but s% was expected."
                    .formatted(WrongArgumentsException.class.getName()));
        } catch (WrongArgumentsException ex) {
            assertEquals(instance.WRONG_NUMBER_OF_ARGS_MESSAGE, ex.getMessage());
        }
    }

    /**
     * Test of getUsageHelp method, of class ArgsParser.
     */
    @Test
    public void testGetUsageHelp() {
        System.out.println("getUsageHelp");
        var instance = new ArgsParser("dummyApp")
                .addPositionalArgument(new Argument("arg1"))
                .addPositionalArgument(new Argument("arg2"));
        var expResult = """
            Usage:
                java -jar dummyApp.jar <arg1> <arg2>""";
        assertEquals(expResult, instance.getUsageHelp());
    }

    /**
     * Test of getArgumentsStructure method, of class ArgsParser.
     */
    @Test
    public void testGetArgumentsStructure() {
        System.out.println("getArgumentsStructure");
        var instance = new ArgsParser("dummyApp")
                .addPositionalArgument(new Argument("arg1"));
        assertEquals("<arg1>", instance.getCLIStructure());
    }

    /**
     * Test of getArgumentList method, of class ArgsParser.
     */
    @Test
    public void testGetArgumentList() {
        System.out.println("getArgumentList");
        var arg1 = new Argument("arg1");
        var instance = new ArgsParser("dummyApp").addPositionalArgument(arg1);
        assertEquals(arg1, instance.getPositionalArgumentList().get(0));
    }

}
