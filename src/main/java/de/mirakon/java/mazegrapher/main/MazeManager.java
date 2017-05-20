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

import de.mirakon.java.mazegrapher.plugins.DummyMaze;
import de.mirakon.java.mazegrapher.plugins.PrimAlgoMazeL2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Stream;

public class MazeManager {

    // TODO: 10.05.2017 prepare for plugin system
    // http://datapile.coffeecrew.org/blog/2013/06/02/creating-a-simple-plugin-mechanism-in-java/
    // http://www.javaranch.com/journal/200607/Plugins.html
    // http://stackoverflow.com/questions/25449/how-to-create-a-pluginable-java-program
    // http://stackoverflow.com/questions/465099/best-way-to-build-a-plugin-system-with-java
    // https://wiki.byte-welt.net/wiki/Java-Programme_durch_PlugIns_erweitern

    // TODO: 10.05.2017 methods to get plugins from jars and classes
    // TODO: 10.05.2017 methods to save/load paths somewhere (preferences)
    @NotNull
    public static TreeMap<String, Maze> getDefaultMazePluginMap() throws MissingMazeArgumentException,
            IllegalStateException {
        return putPluginsInMap(null, getDefaultPlugins());
    }

    @NotNull
    private static TreeMap<String, Maze> putPluginsInMap(@Nullable TreeMap<String, Maze> mazePluginTree,
                                                         @NotNull ArrayList<Class<?>> plugins) throws
            MissingMazeArgumentException, IllegalStateException {
        if (mazePluginTree == null) {
            mazePluginTree = new TreeMap<>();
        }

        Class<?>[] pluginsArray = plugins.stream()
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .filter(Maze.class::isAssignableFrom)
                .filter(clazz -> Stream.of(clazz.getConstructors()).anyMatch(constructor -> constructor
                        .getParameterCount() == 0))
                .toArray(Class<?>[]::new);

        Maze[] mazes = new Maze[pluginsArray.length];
        try {
            for (int i = 0; i < pluginsArray.length; i++) {
                mazes[i] = (Maze) pluginsArray[i].newInstance();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            // Isn't supposed to happen, all Class objects should be filtered away
            e.printStackTrace();
        }

        for (Maze maze : mazes) {
            if ("".equals(maze.getMazeName())) {
                throw new MissingMazeArgumentException(Strings.getString("errorMazeMissingName"));
            }
            if ("".equals(maze.getMazeCategory())) {
                throw new MissingMazeArgumentException(Strings.getString("errorMazeMissingCategory") + " " + maze
                        .getMazeName());
            }
            // TODO: 22.04.2017 use localized Strings for names (provided by mazes)
            String newMazeName = String.format("%s (%s)", maze.getMazeName(), maze.getMazePlugin());
            if ("".equals(maze.getMazePlugin())) {
                throw new MissingMazeArgumentException(Strings.getString("errorMazeMissingPlugin") + " " +
                        newMazeName);
            }
            if (mazePluginTree.putIfAbsent(newMazeName, maze) != null) {
                String duplicateMaze = String.format(" name: %s; category: %s; plugin: %s", maze.getMazeName(), maze
                        .getMazeCategory(), maze.getMazePlugin());
                throw new IllegalStateException(Strings.getString("errorMazeDuplicate") + duplicateMaze);
            }
        }

        return mazePluginTree;
    }

    @NotNull
    private static ArrayList<Class<?>> getDefaultPlugins() {
        ArrayList<Class<?>> defaultPlugins = new ArrayList<>();
        defaultPlugins.add(DummyMaze.class);
        defaultPlugins.add(PrimAlgoMazeL2.class);
        return defaultPlugins;
    }
}
