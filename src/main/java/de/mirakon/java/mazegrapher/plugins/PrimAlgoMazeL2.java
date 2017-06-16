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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
    private boolean[][] maze;

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
        if (height < 3 || width < 3) {
            throw new IllegalArgumentException(Strings.getString(Strings.ERROR_MAZE_GENERATION_BAD_SIZE, height,
                    width));
        }
        this.height = height;
        this.width = width;
        maze = new boolean[width][height];

        // coordinatesWithPossibleConnections are coordinates of intersections which might connect to a new intersection
        Set<Coordinates> coordinatesWithPossibleConnections = new HashSet<>();
        coordinatesWithPossibleConnections.add(setRandomCoordinates());

        while (!coordinatesWithPossibleConnections.isEmpty()) {
            // possibleConnections are coordinates between two intersections
            ArrayList<Coordinates> possibleConnections = findPossibleConnections(coordinatesWithPossibleConnections);
            Coordinates connectionCoordinates = possibleConnections.get(ThreadLocalRandom.current().nextInt(0,
                    possibleConnections.size()));

            setConnection(connectionCoordinates, coordinatesWithPossibleConnections);
        }
        // TODO: 22.05.2017 try multithreading with:
        // TODO: 22.05.2017 a) thread.count = 1; b) thread.count = core.count; c) thread.count = n * core.count
    }

    @NotNull
    private Coordinates setRandomCoordinates() {
        int ranX = ThreadLocalRandom.current().nextInt(1, width - 1);
        int ranY = ThreadLocalRandom.current().nextInt(1, height - 1);
        if (ranX % 2 == 0) {
            ranX--;
        }
        if (ranY % 2 == 0) {
            ranY--;
        }
        maze[ranX][ranY] = true;
        return new Coordinates(ranX, ranY);
    }

    @NotNull
    private ArrayList<Coordinates> findPossibleConnections(@NotNull Set<Coordinates>
                                                                   coordinatesWithPossibleConnections) {
        Iterator<Coordinates> iterator = coordinatesWithPossibleConnections.iterator();
        ArrayList<Coordinates> possibleConnections = new ArrayList<>();
        while (iterator.hasNext()) {
            Coordinates coordinates = iterator.next();
            int possibleConnectionsSize = possibleConnections.size();
            // check if there's any possible connection
            if (coordinates.getX() >= 3 && !maze[coordinates.getX() - 2][coordinates.getY()]) {
                possibleConnections.add(new Coordinates(coordinates.getX() - 1, coordinates.getY()));
            }
            if (coordinates.getX() < width - 3 && !maze[coordinates.getX() + 2][coordinates.getY()]) {
                possibleConnections.add(new Coordinates(coordinates.getX() + 1, coordinates.getY()));
            }
            if (coordinates.getY() >= 3 && !maze[coordinates.getX()][coordinates.getY() - 2]) {
                possibleConnections.add(new Coordinates(coordinates.getX(), coordinates.getY() - 1));
            }
            if (coordinates.getY() < height - 3 && !maze[coordinates.getX()][coordinates.getY() + 2]) {
                possibleConnections.add(new Coordinates(coordinates.getX(), coordinates.getY() + 1));
            }
            // delete coordinates from coordinatesWithPossibleConnections
            if (possibleConnectionsSize == possibleConnections.size()) {
                iterator.remove();
            }
        }
        return possibleConnections;
    }

    private void setConnection(@NotNull Coordinates connectionCoordinates, @NotNull Set<Coordinates>
            coordinatesWithPossibleConnections) {
        Coordinates startCoordinates;
        Coordinates endCoordinates;
        if (connectionCoordinates.getX() % 2 == 0) {
            // connection is vertical
            startCoordinates = new Coordinates(connectionCoordinates.getX(), connectionCoordinates.getY() + 1);
            endCoordinates = new Coordinates(connectionCoordinates.getX(), connectionCoordinates.getY() - 1);
        } else {
            // connection is horizontal
            startCoordinates = new Coordinates(connectionCoordinates.getX() + 1, connectionCoordinates.getY());
            endCoordinates = new Coordinates(connectionCoordinates.getX() - 1, connectionCoordinates.getY());
        }
        maze[connectionCoordinates.getX()][connectionCoordinates.getY()] = true;
        maze[startCoordinates.getX()][startCoordinates.getY()] = true;
        maze[endCoordinates.getX()][endCoordinates.getY()] = true;
        coordinatesWithPossibleConnections.add(startCoordinates);
        coordinatesWithPossibleConnections.add(endCoordinates);
    }
}
