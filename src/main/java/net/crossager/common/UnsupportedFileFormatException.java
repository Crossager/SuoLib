package net.crossager.common;

import java.io.IOException;

public class UnsupportedFileFormatException extends IOException {
    public UnsupportedFileFormatException(){super();}
    public UnsupportedFileFormatException(String message){super(message);}
}
