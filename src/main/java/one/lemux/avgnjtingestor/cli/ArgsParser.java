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

import java.util.ArrayList;
import java.util.List;
import one.lemux.avgnjtingestor.exceptions.WrongArgumentsException;

/**
 * Use this class to process given arguments for CLI applications
 *
 * @author lemux
 */
public class ArgsParser {

    public static final String NO_ARGS_MESSAGE = "No arguments provided.";
    public static final String WRONG_NUMBER_OF_ARGS_MESSAGE = "Three and only three arguments must be provided.";

    private final List<Argument> positionalArguments;
    private final String cliAppName;
    private final StringBuilder argsStructure;

    public ArgsParser(String cliAppName) {
        this.cliAppName = cliAppName;
        this.positionalArguments = new ArrayList();
        this.argsStructure = new StringBuilder();
    }
    
    public ArgsParser addPositionalArgument(String argName) {
        return addPositionalArgument(new Argument(argName));
    }

    public ArgsParser addPositionalArgument(Argument arg) {
        positionalArguments.add(arg);
        generateCLIStructure();
        return this;
    }

    public void parse(String[] args) throws WrongArgumentsException {
        if (!positionalArguments.isEmpty() && (args == null || args.length == 0)) {
            throw new WrongArgumentsException(NO_ARGS_MESSAGE);
        }
        if (args.length != positionalArguments.size()) {
            throw new WrongArgumentsException(WRONG_NUMBER_OF_ARGS_MESSAGE);
        }
        
        for (int i = 0; i < args.length; i++) {
            positionalArguments.get(i).setValue(args[i]);
        }
    }
    
    public String getPositionalArgumentValue(String argumentName) {
        String value = null;
        int i = 0;
        while (value == null && i < positionalArguments.size()) {
            if (positionalArguments.get(i).getName().equals(argumentName))
                value = positionalArguments.get(i).getValue();
        }
        return value;
    }
    
    public List<Argument> getPositionalArgumentList() {
        return positionalArguments;
    }

    public String getUsageHelp() {
        String usage = """
            Usage:
                java -jar %s.jar %s""".formatted(cliAppName, getCLIStructure());
        
        return usage;
    }
    
    public String getCLIStructure() {
        return argsStructure.toString();
    }
    
    private void generateCLIStructure() {
        argsStructure.setLength(0);
        for (Argument arg : positionalArguments) {
            argsStructure.append("<").append(arg.getName()).append("> ");
        }
        argsStructure.setLength(argsStructure.length()-1);
    }
}
