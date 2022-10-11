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
import java.util.Arrays;
import one.lemux.avgnjtingestor.exceptions.EmptyInputFileException;
import one.lemux.avgnjtingestor.exceptions.InvalidInputFileException;

/**
 *
 * @author lemux
 */
public class DataIngestor {

    private final Path pathToFile;
    private BufferedReader bufferedReader;
    private DataFormat currentFormat;
    private String currentLine;
    private String[] dataRow;

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
                bufferedReader = Files.newBufferedReader(this.pathToFile, StandardCharsets.UTF_8);
            } else {
                throw new EmptyInputFileException();
            }
        } else {
            throw new InvalidInputFileException();
        }
    }

    public void ingest(DataQuery query) throws IOException {
        currentLine = bufferedReader.readLine();
        while (currentLine != null) {
            if (currentLine.startsWith("F")) {
                switch (currentLine) {
                    case "F1":
                        currentFormat = DataFormat.F1;
                        break;
                    case "F2":
                        currentFormat = DataFormat.F2;
                        break;
                    default:
                    // TODO handle invalid format specification
                }
            } else if (currentLine.startsWith("D")) {
                extractData();
                if (dataRow != null && dataRow.length == 3) {
                    applyQuery(query);
                } else {
                    // TODO handle line with invalid data
                }
            } else {
                // TODO handle unknown type of line
            }
            currentLine = bufferedReader.readLine();
        }
    }

    private void applyQuery(DataQuery query) {
        if ("CITY".equals(query.getSearchField())) {
            if (query.getSearchTerm().equals(dataRow[1])) {
                System.out.println("%s,%s".formatted(dataRow[0], dataRow[2]));
            }
        } else if ("ID".equals(query.getSearchField())) {
            if (query.getSearchTerm().equals(dataRow[2])) {
                System.out.println(dataRow[1]);
            }
        }
    }

    private void extractData() {
        dataRow = switch (currentFormat) {
            case F1 ->
                currentLine.substring(1).split(",");
            case F2 ->
                currentLine.substring(1).split(" ; ");
            default ->
                null;
        };
        if (dataRow != null) {
            for (int i = 0; i < dataRow.length; i++) {
                dataRow[i] = dataRow[i].strip();
            }
        }
    }

    private enum DataFormat {
        F1, F2
    }
}
