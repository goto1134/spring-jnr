package com.github.goto1134.springjnr;


import jnr.ffi.LibraryLoader;


class SpringJNRLibraryLoader {
    public <T> T load(Class<T> libraryType, LibraryInfo aLibraryInfo) {
        LibraryLoader<T> libraryLoader = LibraryLoader.create(libraryType)
                                                      .convention(aLibraryInfo.getCallingConvention());
        if (!aLibraryInfo.getLibraryPath()
                         .isEmpty()) {
            libraryLoader.search(aLibraryInfo.getLibraryPath());
        }

        if (aLibraryInfo.isFailImmediately()) {
            libraryLoader.failImmediately();
        }

        libraryLoader.mapper(aLibraryInfo.getFunctionMapper());

        return libraryLoader.load(aLibraryInfo.getLibraryName());
    }
}

