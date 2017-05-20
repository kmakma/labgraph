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

import mockit.Deencapsulation;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class CoordinatesTest {

    @Tested
    private Coordinates tested;

    @Injectable
    private int x = ThreadLocalRandom.current().nextInt();

    @SuppressWarnings("FieldCanBeLocal")
    @Injectable
    private int y = 42;

    @Test
    public void Coordinator() throws Exception {
        Coordinates coordinates = new Coordinates(x, y);
        int resultX = Deencapsulation.getField(coordinates, "x");
        int resultY = Deencapsulation.getField(coordinates, "y");
        assertThat(resultX, is(x));
        assertThat(resultY, is(y));
    }

    @Test
    public void getX() throws Exception {
        int result = tested.getX();
        assertThat(result, is(x));
    }

    @Test
    public void setX() throws Exception {
        int randomX = ThreadLocalRandom.current().nextInt();
        tested.setX(randomX);
        int result = Deencapsulation.getField(tested, "x");
        assertThat(result, is(randomX));
    }

    @Test
    public void getY() throws Exception {
        int result = tested.getY();
        assertThat(result, is(y));
    }

    @Test
    public void setY() throws Exception {
        int newY = 24;
        tested.setY(newY);
        int result = Deencapsulation.getField(tested, "y");
        assertThat(result, is(newY));
    }

    @Test
    public void testEquals() throws Exception {
        Coordinates equal = new Coordinates(x, y);
        Coordinates xNotEqual = new Coordinates(x + 1, y);
        Coordinates yNotEqual = new Coordinates(x, y + 1);
        Coordinates bothNotEqual = new Coordinates(x + 1, y + 1);
        class ExtendingClass extends Coordinates {
            private ExtendingClass(int x, int y) {
                super(x, y);
            }
        }
        ExtendingClass extendingClass = new ExtendingClass(x, y);

        // reflexive
        assertThat(tested, equalTo(tested));
        // symmetric
        assertThat(tested, equalTo(equal));
        assertThat(equal, equalTo(tested));
        // different
        assertThat(tested, not(equalTo(xNotEqual)));
        assertThat(tested, not(equalTo(yNotEqual)));
        assertThat(tested, not(equalTo(bothNotEqual)));
        // other
        assertThat(tested, not(equalTo(extendingClass)));
        assertThat(tested, not(equalTo("a string")));
    }

    @Test
    public void testHashCode() throws Exception {
        int equalHashCode = new Coordinates(x, y).hashCode();
        int totallyNotEqualHashCode = new Coordinates(x + 1, y + 1).hashCode();
        assertThat(tested.hashCode(), is(equalHashCode));
        assertThat(tested.hashCode(), not(is(totallyNotEqualHashCode)));
    }
}