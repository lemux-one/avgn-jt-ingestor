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

import one.lemux.avgnjtingestor.cli.Argument;
import one.lemux.avgnjtingestor.cli.ArgsParser;
import java.io.IOException;
import one.lemux.avgnjtingestor.exceptions.EmptyInputFileException;
import one.lemux.avgnjtingestor.exceptions.InvalidInputFileException;
import one.lemux.avgnjtingestor.exceptions.WrongArgumentsException;

/**
 *
 * @author lemux
 */
public class App {

    public static final ArgsParser argsParser
            = new ArgsParser("avgn-jt-ingestor")
                    .addArg(new Argument("inputFile"))
                    .addArg(new Argument("searchField"))
                    .addArg(new Argument("searchTerm"));

    /**
     * Entry point for the application execution.
     *
     * If everything went right then the STDOUT shows the relevant output
     * according to given arguments.
     *
     * All the handled exceptions will stream the relevant output to STDERR. Use
     * pipes or similar techniques to capture the desired outputs. Custom
     * defined exceptions and system runtime errors are treated differently: The
     * former ones are shown in a formatted fashion accompanied by hints if
     * relevant, while the later ones are prefixed by "Error: " with the short
     * description provided by the exception itself.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            argsParser.parse(args);
            var ingestor = new DataIngestor(args[0]);
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (WrongArgumentsException ex) {
            System.err.println(ex.getHintedMessage());
            System.err.println(argsParser.getUsageHelp());
        } catch (InvalidInputFileException | EmptyInputFileException ex) {
            System.err.println(ex.getHintedMessage());
        }
    }
}
