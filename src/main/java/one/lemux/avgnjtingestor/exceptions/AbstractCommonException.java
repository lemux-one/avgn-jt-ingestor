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
package one.lemux.avgnjtingestor.exceptions;

/**
 * This interface provides a common set of behaviors intended to facilitate the
 * implementation of tests and overall handling of the exceptions defined at the
 * project level. It is strongly recommend that all Exception based classes in
 * the project also implement this interface.
 *
 * @author lemux
 */
public abstract class AbstractCommonException extends Exception {

    /**
     * Creates a standard Exception object with the given message.
     *
     * @param message
     */
    public AbstractCommonException(String message) {
        super(message);
    }

    /**
     * Gets a brief description about how to workaround or avoid the reported
     * error.
     *
     * @return some helpful hints to workaround the reported error
     */
    public abstract String getHints();

    /**
     * Gets a well formatted, unified version of the error message and the
     * hints.
     *
     * @return
     */
    public String getHintedMessage() {
        if ("".equals(getHints())) {
            return """
                   Exception:
                   ----------
                   %s""".formatted(getMessage());
        }
        return """
               Exception:
               ----------
               %s
               
               Hints:
               ------
               %s""".formatted(getMessage(), getHints());
    }
}
