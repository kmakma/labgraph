/*
 * Copyright 2017 Michael
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package de.mirakon.java.mazegrapher.main;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class provides a skeletal implementation of the {@link Maze} interface, to minimize the effort required to
 * implement this interface and to lower the risk of errors in the implementation.
 */
public abstract class AbstractMaze implements Maze {

    public AbstractMaze() {
    }

    /**
     * Creates a new instance of a maze implementation, by actually calling {@link Class#newInstance()}.
     * <p>
     * Note that this method will be called when the implementation is used. An accessible nullary constructor has to be
     * present for this method to work.
     *
     * @return a newly allocated instance of a maze implementation
     * @throws InvalidImplementationException if no accessible nullary constructor is present
     */
    @Override
    @NotNull
    public final Maze newInstance() throws InvalidImplementationException {
        try {
            return this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InvalidImplementationException(e);
        }
    }

    /**
     * Returns a description for this maze, by default {@code null}
     *
     * @return the description in a string
     */
    @Override
    @Nullable
    public String getDescription() {
        return null;
    }

    @Override
    public void generate(int height, int width) throws IllegalArgumentException {
        // TODO: 20.05.2017 do not override this
    }
}
