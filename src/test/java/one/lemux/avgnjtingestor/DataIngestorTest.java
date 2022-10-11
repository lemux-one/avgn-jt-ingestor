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
    
    private String INPUT_EXAMPLE_DATA = """
        F1 
        D Erica Burns,BARCELONA,93654902Y 
        D Lucy Mcgee,LONDON,51011156P 
        D Mitchell Newton,SAN FRANCISCO,25384390A 
        D Margarita Richards,LAS VEGAS,09877359D 
        D Rhonda Hopkins,SAN FRANCISCO,54315871Z 
        D Antonia Harper,LAS VEGAS,27466628M 
        D Hilda Caldwell,LONDON,61682270L 
        D Alexander Arnold,SAN FRANCISCO,21743514G 
        D Cheryl Hawkins,LAS VEGAS,76878166E 
        D Renee Anderson,BARCELONA,44340637H 
        F2 
        D Mitchell Newton ; LAS VEGAS ; 25384390-A 
        D Margarita Richards ; NEW YORK ; 09877359-D 
        D Rhonda Hopkins ; BARCELONA ; 54315871-Z 
        D Taylor Matthews ; LISBOA ; 58202263-G 
        D Shelley Payne ; MADRID ; 54808168-L 
        D Johnathan Cooper ; PARIS ; 10863096-N 
        F1 
        D Lowell Miles,BARCELONA,04217040J 
        D Russell Pope,BARCELONA,69429384C 
        D Shelley Payne,BARCELONA,54808168L 
        D Johnathan Cooper,BARCELONA,10863096N 
        D Myra Maldonado,LAS VEGAS,32445934H 
        D Irene Owen,LONDON,15015516N 
        D Susan Holland,SAN FRANCISCO,04810023X 
        D Rodolfo West,LAS VEGAS,74176315G 
        D Peter Daniel,BARCELONA,58204706D 
        F2 
        D Russell Pope ; CARTAGENA ; 69429384-C 
        D Shelley Payne ; OVIEDO ; 54808168-L 
        D Johnathan Cooper ; SANTANDER ; 10863096-N 
        D Myra Maldonado ; MARSELLA ; 32445934-H 
        D Glenn Bryan ; LISBON ; 90844421-X 
        D Neal Love ; SEVILLA ; 52498689-Q 
        D Taylor Matthews ; LONDRES ; 58202263-G 
        F1 
        D Marta Mendez,LAS VEGAS,17200667W 
        D Glenn Bryan,LAS VEGAS,90844421X 
        D Neal Love,LAS VEGAS,52498689Q 
        D Taylor Matthews,LAS VEGAS,58202263G 
        D Tommie Lindsey,LAS VEGAS,12020245P 
        D Ruben Daniels,BARCELONA,84604786E 
        D Emilio Warner,BARCELONA,23803975X 
        D Mark Quinn,LAS VEGAS,82098573G 
        D Dwight Roy,LONDON,87179151C
        D Jake Salazar,SAN FRANCISCO,38399984N 
        D Edna Soto,LAS VEGAS,66991455E 
        """;

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
            var content = """
                F1
                D Erica Burns,BARCELONA,93654902Y
                """;
            Files.writeString(tmpPath, content, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            
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
    
    /**
     * Test of ingest method, of class DataIngestor.
     */
    @Test
    public void testIngest_withExampleDataAndCityQuery() {
        System.out.println("ingest_withExampleDataAndCityQuery");
        try {
            System.out.println("Preparing temp file with content");
            var tmpPath = Files.createTempFile(null, "input.txt");
            Files.writeString(tmpPath, INPUT_EXAMPLE_DATA, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            
            System.out.println("Ingesting given temp file as input: " + tmpPath.toString());
            var query = new DataQuery("CITY", "CARTAGENA");
            DataIngestor instance = new DataIngestor(tmpPath.toString());
            
            var expected = """
                Russell Pope,69429384C
                """;
            
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
