package com.kepler.exception;

public class FileHandlingException extends RuntimeException {
    public FileHandlingException(String message) {
        super(message);
    }

    public FileHandlingException() {
        super("Lỗi khi xử lý file");
    }
}
