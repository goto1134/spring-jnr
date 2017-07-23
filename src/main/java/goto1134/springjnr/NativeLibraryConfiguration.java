package goto1134.springjnr;

/**
 * Inherit from this to declare platform-dependent and runtime-dependent parameters
 */
public interface NativeLibraryConfiguration {

    LibraryInfo getLibraryInfo();
}
