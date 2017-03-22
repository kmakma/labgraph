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

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.TreeMap;

public class MazeCoordinator {

    public static TreeMap<String, Maze> getDefaultMazeMap() {
        return putMazesInMap(null, getDefaultMazes());
    }

    private static TreeMap<String, Maze> putMazesInMap (@Nullable TreeMap<String, Maze> mazeTree, ArrayList<Maze> mazes) {
        if (mazeTree == null) {
            mazeTree = new TreeMap<>();
        }

        for (Maze maze : mazes) {
            if ("".equals(maze.getMazeName())) {
                // TODO: 21.03.2017 throw exception...somebody fucked up a maze ;)
                System.err.println("Temporary Error Message: some idiot tried to smuggle a maze without a name into the system!");
            }

            if(mazeTree.putIfAbsent(maze.getMazeName(), maze) != null) {
                String newMazeName = maze.getMazePlugin();
                if (newMazeName == null || "".equals(newMazeName)) {
                    // TODO: 21.03.2017 throw exception (tried to insert a maze without a plugin name)
                    System.err.println("Temporary Error Message: tried to insert a duplicate maze without a plugin name!");
                } else {
                    newMazeName = String.format("%s (%s)", maze.getMazeName(), newMazeName);
                    if (mazeTree.put(newMazeName, maze) != null) {
                        // TODO: 21.03.2017 throw exception duplicate mazeName & duplicate pluginName
                        System.err.println("Temporary Error Message: tried inserting a maze with a duplicate name and plugin!");
                    }
                }
            }
        }
        return mazeTree;
    }

    @SuppressWarnings("unused")
    @Deprecated
    private static TreeMap<String, TreeMap<String, Maze>> putMazesInMapByCategory(TreeMap<String, TreeMap<String, Maze>> mazeVariations, ArrayList<Maze> mazes) {
        if (mazeVariations == null) {
            mazeVariations = new TreeMap<>();
        }

        TreeMap<String, Maze> mazeCategory;
        for (Maze maze : mazes) {
            if ("".equals(maze.getMazeCategory()) || "".equals(maze.getMazeName())) {
                // TODO LATER: (deprecated) throw exception
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
                        // TODO LATER: (deprecated) throw exception
                        throw new NullPointerException();
                    } else {
                        mazeName = String.format("%s (%s)", maze.getMazeName(), mazeName);
                        if (mazeCategory.putIfAbsent(mazeName, maze) != null) {
                            // TODO LATER: (deprecated) throw exception
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
