# spring-jnr
Spring extension to load native libraries via [jnr-ffi](https://github.com/jnr/jnr-ffi).

**Tests are written for windows**

## Get it
### Gradle
```groovy
dependencies {
    compile group: 'com.github.goto1134', name: 'spring-jnr', version: '1.0'
}
```
### Maven
```xml
<dependencies>
    <dependency>
      <groupId>com.github.goto1134</groupId>
      <artifactId>spring-jnr</artifactId>
      <version>1.0</version>
      <scope>compile</scope>
    </dependency>
</dependencies>
```

## Use it
0. Register `BeanPostProcessor`
```java
@Bean
public BeanPostProcessor nativeLibraryBeanPostProcessor() {
    return new NativeLibraryBeanPostProcessor();
}
```

1. Declare configuration qualifier
```java
@Qualifier
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MicrosoftVisualCRuntime {
}
```
2. Declare library configuration
```java
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
```

3. Mark all libraries with the same qualifier
```java
@MicrosoftVisualCRuntime
public interface PseudoRandomSequenceGenerator {
    int rand();
}
```

4. Mark all fields where injection is needed
```java
@NativeLibrary
private PseudoRandomSequenceGenerator generator;
```