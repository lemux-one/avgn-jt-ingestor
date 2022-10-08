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
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author lemux
 */
public class App {
    
    public static final String ERR_WRONG_ARGS = "Wrong number of arguments: Expected 3, given %s...\n";
    public static final String ERR_WRONG_FILE = "The given input file (%s) does not exist or is not readable...";
    public static final String ERR_EMPTY_FILE = "The given input file (%s) is empty...";

    public static final String HELP = """
        Usage:
            java -jar <application>.jar <input_file> <field> <search_term>
        
        Example:
            /path/to/java -jar /path/to/ingestor.jar /path/to/input.txt ID 12345678L""";

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println(HELP);
        } else if (args.length != 3) {
            System.err.println(ERR_WRONG_ARGS.formatted(args.length));
            System.err.println(HELP);
        } else {
            // check input file before attempting to process it
            Path inputPath = Paths.get(args[0]);
            if (Files.exists(inputPath) && Files.isReadable(inputPath)) {
                try {
                    var bufferedReader = Files.newBufferedReader(inputPath);
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        //process input line by line
                        while (line != null) {
                            line = bufferedReader.readLine();
                        }
                    } else {
                        System.err.println(ERR_EMPTY_FILE.formatted(args[0]));
                    }
                } catch (IOException ex) {
                    System.err.println("Error reading input file: " + ex.getMessage());
                    //TODO log stack trace and details in a file
                }
            } else {
                System.err.println(ERR_WRONG_FILE.formatted(args[0]));
            }
        }
    }
}
