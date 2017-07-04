package goto1134.springjnr;

import goto1134.springjnr.random.PseudoRandomSequenceGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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

    @InjectNativeLibrary
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
}