package goto1134.springjnr;

import java.lang.annotation.*;

/**
 * Use this to mark an interface as native library
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NativeLibrary {
    /**
     * @return name of the library file.
     */
    String libraryName() default "";
}
