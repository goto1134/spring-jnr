package com.github.goto1134.springjnr;

import com.github.goto1134.springjnr.random.PseudoRandomSequenceGenerator;
import jnr.ffi.CallingConvention;
import jnr.ffi.Platform;
import jnr.ffi.provider.IdentityFunctionMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;

/**
 * Created by Andrew
 * on 04.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NativeLibraryBeanPostProcessorTest.class)
@ComponentScan("goto1134.springjnr")
public class NativeLibraryBeanPostProcessorTest {

    @NativeLibrary
    private PseudoRandomSequenceGenerator generator;

    @Test
    public void test()
            throws Exception {
        int rand = generator.rand();
        System.out.println("Spring generator generated " + rand);
        Assert.assertThat(rand, is(greaterThanOrEqualTo(1)));
    }

    @Bean
    public BeanPostProcessor nativeLibraryBeanPostProcessor() {
        return new NativeLibraryBeanPostProcessor();
    }

    @MicrosoftVisualCRuntime
    @Component
    public class MicrosoftVisualCRuntimeConfiguration
            implements NativeLibraryConfiguration {

        @Override
        public LibraryInfo getLibraryInfo() {
            if (Platform.getNativePlatform()
                        .getOS() != Platform.OS.WINDOWS) {
                throw new IllegalStateException("Must be windows OS");
            }
            return new LibraryInfo("msvcrt", "", CallingConvention.STDCALL, true, IdentityFunctionMapper.getInstance());
        }
    }
}