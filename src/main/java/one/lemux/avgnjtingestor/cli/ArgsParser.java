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

import one.lemux.avgnjtingestor.exceptions.WrongArgumentsException;

/**
 * Use this class to process given arguments for CLI applications
 * 
 * @author lemux
 */
public class ArgsParser {
    
    public static final String NO_ARGS_MESSAGE = "No arguments provided.";
    public static final String WRONG_NUMBER_OF_ARGS_MESSAGE = "Only 3 arguments must be provided.";
    
    public static final String USAGE_HELP = """
        Usage:
            java -jar <application>.jar <input_file> <field> <search_term>
        
        Example:
            /path/to/java -jar /path/to/ingestor.jar /path/to/input.txt ID 12345678L""";
    
    
    public ArgsParser() {
        
    }
    
    public void parse(String[] args) throws WrongArgumentsException {
        if (args == null || args.length == 0) {
            throw new WrongArgumentsException(NO_ARGS_MESSAGE);
        } else if (args.length != 3) {
            throw new WrongArgumentsException(WRONG_NUMBER_OF_ARGS_MESSAGE);
        }
    }
}
