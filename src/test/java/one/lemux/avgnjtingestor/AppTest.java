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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import one.lemux.avgnjtingestor.exceptions.EmptyInputFileException;
import one.lemux.avgnjtingestor.exceptions.InvalidInputFileException;
import one.lemux.avgnjtingestor.exceptions.WrongArgumentsException;
import one.lemux.avgnjtingestor.exceptions.WrongFormatException;
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

    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    private final PrintStream sysOut = System.out;
    private final PrintStream sysErr = System.err;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        //captureStreams();
    }

    @After
    public void tearDown() throws Exception {
        //restoreStreams();
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

    /**
     * When given an input file with not well formatted content then STDERR
     * shows a descriptive error message
     */
    //@Test
    public void testMain_whenInputFileIsNotFormatted() {
        System.out.println("main_whenInputFileIsNotFormatted");
        captureStreams();
        try {
            var tmpPath = Files.createTempFile(null, "input.txt");
            var contents = new String[]{
                // cases with no format
                "\n", "  ", "Test Line", "R data", "D With No Previous Format",
                // cases with wrong format specification
                "F", "F0", "F3", "F 1", "F-2",};

            var args = new String[]{tmpPath.toString(), "ID", "54645987"};
            for (String content : contents) {
                Files.writeString(tmpPath, content, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
                App.main(args);
                assertEquals(new WrongFormatException().getHintedMessage() + "\n", errStream.toString());
            }
            Files.deleteIfExists(tmpPath);
        } catch (IOException ex) {
            restoreStreams();
            System.err.println(ex.getMessage());
        } finally {
            restoreStreams();
        }
    }

    /**
     * Capture the system's stdout and stderr streams with clear buffers
     */
    private void captureStreams() {
        outStream.reset();
        errStream.reset();
        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));
    }

    /**
     * Restore the system's stdout and stderr streams
     */
    private void restoreStreams() {
        System.setOut(sysOut);
        System.setErr(sysErr);
    }
}
