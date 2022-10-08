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

/**
 *
 * @author lemux
 */
public class App {

    public static final String help
            = """
        Usage:
            java -jar <application>.jar <input_file> <field> <search_term>
        
        Example:
            /path/to/java -jar /path/to/ingestor.jar /path/to/input.txt ID 12345678L""";

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println(help);
        } else if (args.length != 3) {
            System.err.println("Wrong number of arguments: Expected 3, given %s...\n".formatted(args.length));
            System.err.println(help);
        } else {
            
        }
    }
}
