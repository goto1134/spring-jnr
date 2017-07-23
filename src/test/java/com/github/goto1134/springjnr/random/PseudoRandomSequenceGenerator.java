package com.github.goto1134.springjnr.random;

import com.github.goto1134.springjnr.MicrosoftVisualCRuntime;

/**
 * Interface for <a href="http://en.cppreference.com/w/c/numeric/random">Pseudo-random number generation</a> methods
 * of C stdlib
 */
@MicrosoftVisualCRuntime
public interface PseudoRandomSequenceGenerator {
    int rand();
}
