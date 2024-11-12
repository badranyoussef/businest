package org.util;

import org.entities.FileRequirements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

class FileValidatorTest {

    private FileValidator fileValidator = new FileValidator();
    private FileRequirements testFileReq;
    private char[] symbols = {' ', '%', '"', '\''};
    private int length = 10;
    private double fileSizeBytes = 34;
    private Set<String> extensionsAllowed = Set.of(".txt", ".pdf");
    private File file12byte = new File("businest/backend/src/test/java/testingUtitlites/file_12Byte.txt");
    private File file48byte = new File("businest/backend/src/test/java/testingUtitlites/file_48Byte.txt");

    @BeforeEach
    public void beforeEach() {
        testFileReq = new FileRequirements( symbols, length, fileSizeBytes, extensionsAllowed);
    }

    @Test
    void validateFilePositive() {
        boolean actual = fileValidator.validateFile(file12byte, testFileReq);
    }

    @Test
    void validateExtension() {

    }

    @Test
    void validateTitleLength() {
    }

    @Test
    void validateFileSize() {
    }

    @Test
    void validateTitleSymbolsAllowed() {
    }
}