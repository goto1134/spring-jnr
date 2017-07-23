package com.github.goto1134.springjnr.random;

import jnr.ffi.LibraryLoader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;

/**
 * Created by Andrew
 * on 04.07.2017.
 */
@RunWith(JUnit4.class)
public class PseudoRandomSequenceGeneratorTest {

    private static PseudoRandomSequenceGenerator generator;

    @BeforeClass
    public static void loadLibrary()
            throws Exception {
        generator = LibraryLoader.create(PseudoRandomSequenceGenerator.class)
                                 .load("msvcrt");
    }

    @Test
    public void name()
            throws Exception {
        int rand = generator.rand();
        System.out.println("General generator generated " + rand);
        Assert.assertThat(rand, is(greaterThanOrEqualTo(1)));
    }
}
