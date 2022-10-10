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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import one.lemux.avgnjtingestor.exceptions.EmptyInputFileException;
import one.lemux.avgnjtingestor.exceptions.InvalidInputFileException;

/**
 *
 * @author lemux
 */
public class DataIngestor {

    private final Path pathToFile;
    private BufferedReader bufferedReader = null;

    /**
     * Creates a DataIngestor given an input text file. If the file is valid and
     * has some content then you can attempt to process it by calling the ingest
     * method, otherwise proper exceptions are thrown and should be handled by
     * the calling context.
     *
     * @param inputFile
     * @throws IOException
     * @throws InvalidInputFileException
     * @throws EmptyInputFileException
     */
    public DataIngestor(String inputFile) throws IOException, InvalidInputFileException, EmptyInputFileException {
        this.pathToFile = Path.of(inputFile);
        if (Files.isRegularFile(pathToFile) && Files.isReadable(pathToFile)) {
            if (Files.size(pathToFile) > 0) {
                this.bufferedReader = Files.newBufferedReader(this.pathToFile, StandardCharsets.UTF_8);
            } else {
                throw new EmptyInputFileException();
            }
        } else {
            throw new InvalidInputFileException();
        }
    }

    public void ingest(DataQuery query) {
        
    }
}
