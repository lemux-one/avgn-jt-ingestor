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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import one.lemux.avgnjtingestor.exceptions.EmptyInputFileException;
import one.lemux.avgnjtingestor.exceptions.InvalidInputFileException;
import static one.lemux.avgnjtingestor.IOStreamsManager.*;
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
public class DataIngestorTest {

    public DataIngestorTest() {
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
     * Test of ingest method, of class DataIngestor.
     */
    @Test
    public void testIngest() {
        System.out.println("ingest");
        try {
            System.out.println("Preparing temp file with content");
            var tmpPath = Files.createTempFile(null, "input.txt");
            var contents = new String[]{
                "F1",
                "D Erica Burns,BARCELONA,93654902Y"
            };
            for (var content : contents) {
                Files.writeString(tmpPath, content, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            }
            
            System.out.println("Ingesting given temp file as input: " + tmpPath.toString());
            var query = new DataQuery("CITY", "BARCELONA");
            DataIngestor instance = new DataIngestor(tmpPath.toString());
            var expected = "Erica Burns,93654902Y\n";
            captureStreams();
            instance.ingest(query);
            var result = outStream.toString();
            restoreStreams();
            Files.deleteIfExists(tmpPath);
            assertEquals(expected, result);
        } catch (IOException ex) {
            fail(ex.getMessage());
        } catch (InvalidInputFileException | EmptyInputFileException ex) {
            fail("The temp input file was empty or not accessible");
        }

    }

}
