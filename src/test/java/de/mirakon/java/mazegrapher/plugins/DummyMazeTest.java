/*
 * Copyright 2017 michael
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

import de.mirakon.java.mazegrapher.main.Strings;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by RACEMAT on his birthday 2017.
 * TDD FTW FOCK YEAH
 */
public class DummyMazeTest {

    @Tested
    private DummyMaze tested;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mocked
    private Strings strings;

    @Test
    public void generateWithException1() throws Exception {
        new StringsExpectations();

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("bloop");

        tested.generate(2, 5);
    }

    @Test
    public void generateWithException2() throws Exception {
        new StringsExpectations();

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("bloop");

        tested.generate(20, 1);
    }

    @Test
    public void generate3x3() throws Exception {

        boolean[][] matrix = new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, false, false}
        };

        tested.generate(3, 3);

        boolean[][] result = Deencapsulation.getField(tested, "maze");

        assertThat(result.length, is(3));
        assertThat(result[0].length, is(3));

        assertThat(result[0], is(matrix[0]));
        assertThat(result[1], is(matrix[1]));
        assertThat(result[2], is(matrix[2]));
    }

    @Test
    public void generate4x4() throws Exception {

        boolean[][] matrix = new boolean[][]{
                {false, false, false, false},
                {false, true, false, false},
                {false, false, true, false},
                {false, false, false, false}
        };

        tested.generate(4, 4);

        boolean[][] result = Deencapsulation.getField(tested, "maze");

        assertThat(result.length, is(4));
        assertThat(result[0].length, is(4));

        assertThat(result[0], is(matrix[0]));
        assertThat(result[1], is(matrix[1]));
        assertThat(result[2], is(matrix[2]));
        assertThat(result[3], is(matrix[3]));
    }

    @Test
    public void generate5x5() throws Exception {

        boolean[][] matrix = new boolean[][]{
                {false, false, false, false, false},
                {false, true, false, true, false},
                {false, false, true, false, false},
                {false, true, false, true, false},
                {false, false, false, false, false}
        };

        tested.generate(5, 5);

        boolean[][] result = Deencapsulation.getField(tested, "maze");

        assertThat(result.length, is(5));
        assertThat(result[0].length, is(5));

        assertThat(result[0], is(matrix[0]));
        assertThat(result[1], is(matrix[1]));
        assertThat(result[2], is(matrix[2]));
        assertThat(result[3], is(matrix[3]));
        assertThat(result[4], is(matrix[4]));
    }

    private final class StringsExpectations extends Expectations {
        StringsExpectations() {
            super();
            Strings.getString(anyString, anyInt, anyInt);
            result = "bloop";
        }
    }
}