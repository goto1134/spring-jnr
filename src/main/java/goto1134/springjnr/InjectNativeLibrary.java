package goto1134.springjnr;

import java.lang.annotation.*;

/**
 * Use this on field to inject {@link NativeLibrary}-annotated interface
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectNativeLibrary {
}
