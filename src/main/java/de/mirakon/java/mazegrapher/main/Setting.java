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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Setting {
    MAX_MAZE_HEIGHT("maximumMazeHeight", 20),
    MAX_MAZE_WIDTH("maximumMazeWidth", 20),
    MIN_MAZE_HEIGHT("minimumMazeHeight", 10),
    MIN_MAZE_WIDTH("minimumMazeWidth", 10);

    private final String key;
    private final int defaultValue;

    public static final int DEFAULT_INT = Integer.MIN_VALUE;

    Setting(String key, int defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Contract(pure = true)
    @NotNull
    public String key() {
        return key;
    }

    @Contract(pure = true)
    public int defaultValue() {
        return defaultValue;
    }
}
