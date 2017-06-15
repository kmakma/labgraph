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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GenericMethods {
    /**
     * Returns the n-th element of given set. With an index of smaller than 0 or bigger than the set size no element can
     * be found
     *
     * @param set   to find the element in
     * @param index of the desired element, whereas 0 is the first element
     * @param <E>   the type of elements maintained by this set
     * @return the n-th element in the set or {@code null} if there is no n-th element
     * @throws IllegalArgumentException if {@code index < 0} or {@code set.size() - 1 < index}
     */
    @Nullable
    @Contract(pure = true)
    public static <E> E getElementXFromSet(Set<E> set, int index) throws IllegalArgumentException {
        int i = 0;
        for (E element : set) {
            if (index == i) {
                return element;
            }
            i++;
        }
        // TODO: 15.06.2017 put message to exception:
        throw new IllegalArgumentException();
    }
}
