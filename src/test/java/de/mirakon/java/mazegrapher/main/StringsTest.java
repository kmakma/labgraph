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
import mockit.Expectations;
import mockit.Injectable;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StringsTest {

    // http://stackoverflow.com/questions/38797588/verify-that-a-private-method-was-not-executed-jmockit

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Injectable
    private ResourceBundle mockedStrings;

    /**
     * {@link Strings#getString(String)}
     * <p>
     * case: {@code strings != null}
     */
    @Test
    public void getStringKeyV1() throws Exception {
        String key = "a key";
        String value = "string for given key";
        Deencapsulation.setField(Strings.class, "strings", mockedStrings);

        new StringsTestExpectations(key, value, 0, 1);

        String result = Strings.getString(key);
        assertThat(result, is(value));
    }

    /**
     * {@link Strings#getString(String)}
     * <p>
     * case: {@code strings == null}
     */
    @Test
    @Ignore
    public void getStringKeyV2() throws Exception {
        String key = "a key";
        String value = "string for given key";

        Deencapsulation.setField(Strings.class, "strings", null);

        new Expectations(Strings.class) {
            {
                Deencapsulation.invoke(Strings.class, "loadStringResources");
                times = 1;
                Deencapsulation.setField(Strings.class, "strings", mockedStrings);
                mockedStrings.getString(key);
                result = value;
            }
        };

        String result = Strings.getString(key);
        assertThat(result, is(value));
    }

    /**
     * {@link Strings#getString(String)}
     * <p>
     * case: {@code strings != null} & no value for the given key
     */
    @Test
    public void getStringKeyV3(@Injectable MissingResourceException e) throws Exception {

        String key = "a key";
        String value = "string for given key";
        Deencapsulation.setField(Strings.class, "strings", mockedStrings);

        new StringsTestExpectations(key, e, 0, 1);

        expectedException.expect(is(e));

        Strings.getString(key);
    }

    @Test
    public void getStringKeyAndValuesV1() throws Exception {

    }

    private final class StringsTestExpectations extends Expectations {
        StringsTestExpectations(String key, Object value, int loadStringResourcesTimes, int
                getStringTimes) {
            super(Strings.class);
            {
                Deencapsulation.invoke(Strings.class, "loadStringResources");
                times = loadStringResourcesTimes;
                mockedStrings.getString(key);
                result = value;
                times = getStringTimes;
            }
        }
    }

}