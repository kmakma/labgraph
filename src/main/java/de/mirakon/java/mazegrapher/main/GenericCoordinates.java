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
 * Integer coordinates with a generic value
 *
 * @param <V> value of the coordinates
 */
public class GenericCoordinates<V> extends Coordinates {

    private V value;

    /**
     * Allocates a {@code GenericCoordinates} objects with the coordinates {@code x} and {@code y} and a generic
     * {@value}, for example a boolean.
     *
     * @param x     integer for the x coordinate
     * @param y     integer for the y coordinate
     * @param value generic object for the generic value
     */
    public GenericCoordinates(int x, int y, V value) {
        super(x, y);
        setValue(value);
    }

    /**
     * Get the generic value
     *
     * @return generic object representig the value
     */
    public V getValue() {
        return value;
    }

    /**
     * Set the value
     *
     * @param value generic value for this object
     */
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GenericCoordinates<?> that = (GenericCoordinates<?>) o;

        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue());
    }
}
