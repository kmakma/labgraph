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

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.TreeMap;

public class MazeCoordinator {

    public static TreeMap<String, TreeMap<String, Maze>> getDefaultMazeVariations() {
        return foo(null, getDefaultMazes());
    }

    // FIXME: 21.03.2017 variablen deklarationen (in allen klassen) in die schleifen reinpacken wenn nicht außerhalb nötig

    private static TreeMap<String, TreeMap<String, Maze>> foo(@Nullable TreeMap<String, TreeMap<String, Maze>> mazeVariations, ArrayList<Maze> mazes) {
        if (mazeVariations == null) {
            mazeVariations = new TreeMap<>();
        }

        TreeMap<String, Maze> mazeCategory;
        for (Maze maze : mazes) {
            if (maze.getMazeCategory() == null || "".equals(maze.getMazeCategory()) || maze.getMazeName() == null || "".equals(maze.getMazeName())) {
                // TODO: 20.03.2017 throw some nice exception
                throw new NullPointerException();
            }

            if ((mazeCategory = mazeVariations.get(maze.getMazeCategory())) == null) {
                mazeCategory = new TreeMap<>();
                mazeCategory.put(maze.getMazeName(), maze);
                mazeVariations.put(maze.getMazeCategory(), mazeCategory);
            } else {
                if (mazeCategory.putIfAbsent(maze.getMazeName(), maze) != null) {
                    String mazeName = maze.getMazePlugin();
                    if (mazeName == null || "".equals(mazeName)) {
                        // TODO: 20.03.2017 throw exception (maze xy hat keinen plugin name)
                        throw new NullPointerException();
                    } else {
                        mazeName = String.format("%s (%s)", maze.getMazeName(), mazeName);
                        if (mazeCategory.putIfAbsent(mazeName, maze) != null) {
                            // TODO: 20.03.2017 throw exception duplicate mazeName in plugin xy
                        }
                    }
                }
            }
        }
        return mazeVariations;
    }

    private static ArrayList<Maze> getDefaultMazes() {
        ArrayList<Maze> defaultMazes = new ArrayList<>();
        defaultMazes.add(new DummyMaze());
        return defaultMazes;
    }
}
