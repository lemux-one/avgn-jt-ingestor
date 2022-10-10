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
package one.lemux.avgnjtingestor;

import java.io.IOException;
import java.nio.file.Files;
import static one.lemux.avgnjtingestor.IOStreamsManager.*;
import one.lemux.avgnjtingestor.exceptions.EmptyInputFileException;
import one.lemux.avgnjtingestor.exceptions.InvalidInputFileException;
import one.lemux.avgnjtingestor.exceptions.WrongArgumentsException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * When given no arguments STDOUT must empty and STDERR must show a relevant
     * error message and the usage help.
     */
    @Test
    public void testMain_whenNoArguments() {
        System.out.println("main_whenNoArguments");
        captureStreams();
        var args_pool = new String[][]{null, {}};
        for (String[] args : args_pool) {
            captureStreams();
            App.main(args);
            assertEquals("", outStream.toString());
            assertEquals(
                    new WrongArgumentsException(App.argsParser.NO_ARGS_MESSAGE).getHintedMessage()
                    + "\n" + App.argsParser.getUsageHelp() + "\n",
                    errStream.toString());
        }
        restoreStreams();
    }

    /**
     * When given the wrong number of arguments STDOUT must be empty and STDERR
     * shows a descriptive error and the help message
     */
    @Test
    public void testMain_whenWrongNumberOfArguments() {
        System.out.println("main_whenWrongNumberOfArguments");
        captureStreams();
        var args_pool = new String[][]{
            {"first"},
            {"first", "second"},
            {"first", "second", "third", "fourth"}
        };
        for (String[] args : args_pool) {
            captureStreams();
            App.main(args);
            assertEquals("", outStream.toString());
            assertEquals(
                    new WrongArgumentsException(App.argsParser.WRONG_NUMBER_OF_ARGS_MESSAGE).getHintedMessage()
                    + "\n" + App.argsParser.getUsageHelp() + "\n",
                    errStream.toString()
            );
        }
        restoreStreams();
    }

    /**
     * When given a complete set of expected arguments and the input file is
     * invalid (inexistent or inaccessible) STDOUT must be empty and STDERR
     * shows a descriptive error message
     */
    @Test
    public void testMain_whenWrongInputFile() {
        System.out.println("main_whenWrongInputFile");
        captureStreams();
        var args = new String[]{"/path/to/invalid.txt", "<field>", "<search_term>"};
        App.main(args);
        assertEquals("", outStream.toString());
        assertEquals(new InvalidInputFileException().getHintedMessage() + "\n", errStream.toString());
        restoreStreams();
    }

    /**
     * When given a complete set of expected arguments and the input file is
     * valid but empty then STDOUT must be empty and STDERR shows a descriptive
     * error message
     */
    @Test
    public void testMain_whenEmptyInputFile() {
        System.out.println("main_whenEmptyInputFile");
        captureStreams();
        try {
            var tmpPath = Files.createTempFile(null, "input.txt");
            var args = new String[]{tmpPath.toString(), "city", "Barcelona"};
            App.main(args);
            assertEquals("", outStream.toString());
            assertEquals(new EmptyInputFileException().getHintedMessage() + "\n", errStream.toString());
            Files.deleteIfExists(tmpPath);
        } catch (IOException ex) {
            restoreStreams();
            System.err.println(ex.getMessage());
        } finally {
            restoreStreams();
        }
    }

}
