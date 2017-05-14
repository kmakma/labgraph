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

import java.util.prefs.Preferences;

public class SettingsController {
    public static final String ALL_SETTINGS_SET = "allSettingsSet";
    public static final String MAX_X_MAZE_SIZE = "maximumXMazeSize";
    public static final String MAX_Y_MAZE_SIZE = "maximumYMazeSize";
    public static final String MIN_X_MAZE_SIZE = "minimumXMazeSize";
    public static final String MIN_Y_MAZE_SIZE = "minimumYMazeSize";

    private static final int DEFAULT_MAX_X_MAZE_SIZE = 20;
    private static final int DEFAULT_MAX_Y_MAZE_SIZE = 20;
    private static final int DEFAULT_MIN_X_MAZE_SIZE = 10;
    private static final int DEFAULT_MIN_Y_MAZE_SIZE = 10;

//    private Preferences settings = Preferences.userNodeForPackage(this.getClass());

    public static void setDefaultSettings() {
        Preferences settings = Preferences.userNodeForPackage(SettingsController.class);
        settings.putInt(MAX_X_MAZE_SIZE, DEFAULT_MAX_X_MAZE_SIZE);
        settings.putInt(MAX_Y_MAZE_SIZE, DEFAULT_MAX_Y_MAZE_SIZE);
        settings.putInt(MIN_X_MAZE_SIZE, DEFAULT_MIN_X_MAZE_SIZE);
        settings.putInt(MIN_Y_MAZE_SIZE, DEFAULT_MIN_Y_MAZE_SIZE);
        settings.putBoolean(ALL_SETTINGS_SET, true);
    }
}
