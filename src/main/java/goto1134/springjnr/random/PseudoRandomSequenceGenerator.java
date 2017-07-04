package goto1134.springjnr.random;

import goto1134.springjnr.NativeLibrary;

/**
 * Interface for <a href="http://en.cppreference.com/w/c/numeric/random">Pseudo-random number generation</a> methods
 * of C stdlib
 */
@NativeLibrary(libraryName = "msvcrt")
public interface PseudoRandomSequenceGenerator {
    int rand();
}
