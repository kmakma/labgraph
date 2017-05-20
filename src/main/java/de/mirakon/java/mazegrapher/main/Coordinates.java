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

import java.util.Objects;

/**
 * Integer coordinates for any use.
 */
public class Coordinates {

    private int x;
    private int y;

    /**
     * Allocates a {@code Coordinates} object, with two coordinates.
     *
     * @param x value for the x coordinate
     * @param y value for the y coordinate
     */
    public Coordinates(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Get the x coordinate
     *
     * @return integer representing the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Set the x coordinate
     *
     * @param x value for the coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the y coordinate
     *
     * @return integer representing the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Set the y coordinate
     *
     * @param y value for the coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
