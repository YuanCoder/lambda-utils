package com.extendus.lamdautils;

import com.extendus.lamdautils.function.ThrowFunction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ThrowFunctionTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ThrowFunctionTest.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() throws Exception {

    }

    @Test
    public void successTest() throws Exception {
        assertThat(true, is(true));
    }

    @Test
    public void failureTest() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("this is an exception");

        Function<String, IllegalArgumentException> illegalArgumentException = IllegalArgumentException::new;

        ThrowFunction<String> throwIllegalStateException = m -> {
            throw illegalArgumentException.apply(m);
        };

        throwIllegalStateException.lob("this is an exception");
    }

}