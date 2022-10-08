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
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        captureStreams();
    }

    @After
    public void tearDown() throws Exception {
        restoreStreams();
    }

    /**
     * When given no arguments STDOUT must empty and STDERR must show the help
     * message
     */
    @Test
    public void testMainWithNoArguments() {
        var args_pool = new String[][]{null, {}};
        for (int i = 0; i < args_pool.length; i++) {
            captureStreams();
            App.main(args_pool[i]);
            assertEquals("", outStream.toString());
            assertEquals(App.HELP + "\n", errStream.toString());
        }
    }

    /**
     * When given the wrong number of arguments STDOUT must be empty and STDERR
     * shows a descriptive error and the help message
     */
    @Test
    public void testMainWithWrongNumberOfArguments() {
        var args_pool = new String[][]{
            {"first"},
            {"first", "second"},
            {"first", "second", "third", "fourth"}
        };
        var expectedError = """
            Wrong number of arguments: Expected 3, given %s...
            
            %s
            """;
        String[] args = null;
        for (int i = 0; i < args_pool.length; i++) {
            args = args_pool[i];
            captureStreams();
            App.main(args);
            assertEquals("", outStream.toString());

            assertEquals(
                    expectedError.formatted(args.length, App.HELP),
                    errStream.toString()
            );
        }
    }

    /**
     * When given a complete set of expected arguments and the input file is
     * invalid (inexistent or inaccessible) STDOUT must be empty and STDERR
     * shows a descriptive error message
     */
    @Test
    public void testMainWithWrongInputFile() {
        var args = new String[]{"/path/to/invalid.txt", "<field>", "<search_term>"};
        App.main(args);
        assertEquals("", outStream.toString());
        assertEquals(App.ERR_WRONG_FILE.formatted(args[0]) + "\n", errStream.toString());
    }

    /**
     * When given a complete set of expected arguments and the input file is
     * valid but empty then STDOUT must be empty and STDERR shows a descriptive
     * error message
     */
    @Test
    public void testMainWithEmptyInputFile() {
        try {
            var tmpPath = Files.createTempFile(null, "input.txt");
            var args = new String[]{tmpPath.toString(), "<field>", "<search_term>"};
            App.main(args);
            assertEquals("", outStream.toString());
            assertEquals(App.ERR_EMPTY_FILE.formatted(args[0]) + "\n", errStream.toString());
        } catch (IOException ex) {
            restoreStreams();
            System.err.println(ex.getMessage());
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
