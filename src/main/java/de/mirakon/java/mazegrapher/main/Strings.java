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

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Strings {

    /**
     * The key to the string: "{@code Tried to create a maze with an illegal size; height: {0}, width: {1}!}" with
     * {@code {0}} and {@code {1}} being placeholders for the corresponding values.
     * <p>
     * Use {@link Strings#getString(String, Object...)} with desired values for best effect.
     */
    public static final String ERROR_MAZE_GENERATION_BAD_SIZE = "errorMazeGenerationBadSize";

    private static ResourceBundle strings;

    /**
     * Gets a string for the given key from the resources.
     *
     * @param key the key for the desired string
     * @return the string for the given key
     * @throws MissingResourceException if no object for the given key can be found
     */
    @NotNull
    public static String getString(@NotNull String key) throws MissingResourceException {
        if (strings == null) {
            loadStringResources();
        }
        return strings.getString(key);
    }

    /**
     * Get a string for the given key from the resources and format it with given values.
     *
     * @param key    the key for the desired string
     * @param values values to format the string
     * @return the formatted string for the given key and values
     * @throws MissingResourceException if no object for the given key can be found
     * @throws IllegalArgumentException if other {@code values} were expected
     */
    @NotNull
    public static String getString(@NotNull String key, Object... values) throws MissingResourceException,
            IllegalArgumentException {
        if (strings == null) {
            loadStringResources();
        }
        return MessageFormat.format(strings.getString(key), values);
    }

    /**
     * Loads the Strings.properties (or Strings_xx.properties) ResourceBundle containing all location dependent
     * strings, with current default Locale
     */
    private static void loadStringResources() {
        strings = ResourceBundle.getBundle("Strings");
    }
}
