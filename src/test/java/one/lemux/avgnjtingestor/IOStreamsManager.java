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
import java.io.PrintStream;

/**
 *
 * @author lemux
 */
public class IOStreamsManager {
    
    public static final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    public static final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    public static final PrintStream sysOut = System.out;
    public static final PrintStream sysErr = System.err;
    
    /**
     * Capture the system's stdout and stderr streams with clear buffers
     */
    public static void captureStreams() {
        outStream.reset();
        errStream.reset();
        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));
    }

    /**
     * Restore the system's stdout and stderr streams
     */
    public static void restoreStreams() {
        System.setOut(sysOut);
        System.setErr(sysErr);
    }
}
