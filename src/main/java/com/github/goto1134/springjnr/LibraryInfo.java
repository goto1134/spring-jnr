package com.github.goto1134.springjnr;

import jnr.ffi.CallingConvention;
import jnr.ffi.mapper.FunctionMapper;
import jnr.ffi.provider.IdentityFunctionMapper;

public class LibraryInfo {
    public static LibraryInfo defaultLibraryInfo(String aLibraryName) {
        return new LibraryInfo(aLibraryName, "", CallingConvention.DEFAULT,
                               true, IdentityFunctionMapper.getInstance());
    }

    private final String libraryName;
    private final String libraryPath;
    private final CallingConvention callingConvention;
    private final boolean failImmediately;
    private final FunctionMapper functionMapper;

    public LibraryInfo(String libraryName,
                       String libraryPath,
                       CallingConvention callingConvention,
                       boolean failImmediately, FunctionMapper functionMapper) {
        this.libraryName = libraryName;
        this.libraryPath = libraryPath;
        this.callingConvention = callingConvention;
        this.failImmediately = failImmediately;
        this.functionMapper = functionMapper;
    }

    public String getLibraryPath() {
        return libraryPath;
    }

    public CallingConvention getCallingConvention() {
        return callingConvention;
    }

    public boolean isFailImmediately() {
        return failImmediately;
    }

    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }

    public String getLibraryName() {
        return libraryName;
    }
}
