/*
 * Copyright 2017 Michael
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.mirakon.java.stuff.mazegrapher.mazes;

import java.sql.SQLException;

/**
 * Thrown to indicate that a method returned an illegal or inappropriate argument.
 */
public class MissingMazeArgumentException extends RuntimeException {
    private static final long serialVersionUID = 5486176906632218144L;

    public MissingMazeArgumentException() {
        super();
    }

    public MissingMazeArgumentException(String message) {
        super(message);
    }

    public MissingMazeArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingMazeArgumentException(Throwable cause) {
        super(cause);
    }

    private void test() throws SQLException {
        throw new SQLException();
    }
}
