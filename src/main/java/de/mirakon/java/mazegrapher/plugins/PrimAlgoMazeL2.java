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

package de.mirakon.java.mazegrapher.plugins;

import de.mirakon.java.mazegrapher.main.AbstractMaze;
import de.mirakon.java.mazegrapher.main.Coordinates;
import de.mirakon.java.mazegrapher.main.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A maze of this class is created by using a randomized version of prim's algorithm (sort of creating a minimal
 * spanning tree). During generation, paths of length two are constructed from possible junctions creating new junctions
 * (therefore the class suffix "L2")
 */
public class PrimAlgoMazeL2 extends AbstractMaze {

    private static final String mazeCategory = "Perfect Mazes";
    private static final String mazeName = "PrimAlgoL2";
    private static final String mazePlugin = "default";
    private int height;
    private int width;

    @SuppressWarnings("unused")
    public PrimAlgoMazeL2() {
    }

    @Override
    @NotNull
    public String getMazeCategory() {
        return mazeCategory;
    }

    @Override
    @NotNull
    public String getMazeName() {
        return mazeName;
    }

    @Override
    @NotNull
    public String getMazePlugin() {
        return mazePlugin;
    }

    @Override
    public @Nullable String getDescription() {
        return "This is maze will be created with a randomized version of prim's algorithm.";
    }

    @Override
    public void generate(int height, int width) throws IllegalArgumentException {
        // TODO: 20.05.2017 generate ^^
        if (height < 3 || width < 3) {
            throw new IllegalArgumentException(Strings.getString(Strings.ERROR_MAZE_GENERATION_BAD_SIZE, height,
                    width));
        }
        this.height = height;
        this.width = width;
        Map<Coordinates, Boolean> mazes = new HashMap<>();

        // TODO: 20.05.2017 befülle maze mit standardzeug
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // TODO: 21.05.2017 create walls everywhere
            }
        }

        // TODO: 20.05.2017 erstes feld zufällig wählen
        int zufX = ThreadLocalRandom.current().nextInt(1, height - 1);
        int zufY = ThreadLocalRandom.current().nextInt(1, width - 1);
        Set<Coordinates> coordinatesWithPossibleConnections = new HashSet<>();
        // TODO: 20.05.2017 erste feld zu nodesWithPossibleConnections hinzufügen
        // TODO: 20.05.2017 findPossibleConnections, und aktualisiere dabei nWPC
        // TODO: 20.05.2017 wähle connection füg sie hinzu und wiederhole
    }
}
