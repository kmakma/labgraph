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
 * An object representing a way to create a maze. It's recommended to use {@link AbstractMaze} for implementations of
 * {@code Maze} plugins, instead of the interface itself.
 * <p>
 * Duplicate plugins, same name, cateogry and plugin name, are not allowed.
 */
public interface Maze {
    // LabyrinthA , ~B, ~D sind die vielversprechendsten

    /**
     * Creates a new instance of a maze implementation.
     *
     * @return a newly allocated instance of a maze implementation
     * @throws InvalidImplementationException if the implementation of {@code Maze} is invalid
     */
    @NotNull
    Maze newInstance() throws InvalidImplementationException;

    /**
     * Returns the category of this maze
     *
     * @return a string representing the {@code mazeCategory}
     */
    @NotNull
    String getMazeCategory();

    /**
     * Returns the name of this maze.
     *
     * @return a string representing the {@code mazeName}
     */
    @NotNull
    String getMazeName();

    /**
     * Returns the plugin name of this maze
     *
     * @return a string representing the {@code mazePlugin}
     */
    @NotNull
    String getMazePlugin();

    /**
     * Returns a description for this maze
     *
     * @return the description in a string
     */
    @Nullable
    String getDescription();

    /**
     * Returns a two dimensional boolean array representing this maze
     *
     * @return a boolean array with {@code [height][width]} and {@code true} equals path, {@code false} equals wall
     */
    @NotNull
    boolean[][] getMaze();

    /**
     * Generates a maze with the maximum size, walls around included, as specified by parameters. Since some maze
     * generation algorithms cannot handle even or odd sizes the accessible part of the maze might be smaller than
     * expected.
     *
     * @param height of the maze, walls on both sides included
     * @param width  of the maze, walls on both sides included
     * @throws IllegalArgumentException if sizes are negative or too small
     */
    void generate(int height, int width) throws IllegalArgumentException;

    // TODO: 22.05.2017 add optional method "performanceTest"
}
