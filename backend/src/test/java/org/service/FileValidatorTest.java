package org.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.persistence.model.FileRequirements;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.Assert.assertEquals;

class FileValidatorTest {

    private FileValidator fileValidator;
    private FileRequirements testFileReq;
    private char[] symbols = {' ', '%', '"', '\''};
    private int maxLength = 5;
    private double fileSizeBytes = 34;
    private Set<String> extensionsAllowed = Set.of(".txt", ".pdf");

    private URL url12byte = getClass().getResource("12B.txt");
    private URL urlAcceptedFile = getClass().getResource("good.txt");
    private URL url48byte = getClass().getResource("48B.txt");
    private URL urlLong = getClass().getResource("looong.txt");
    private URL urlShort = getClass().getResource("short.txt");
    private URL urlMissingTitle = getClass().getResource(".txt");
    private URL urlFileType = getClass().getResource("type.js");
    private URL urlFileSumbols = getClass().getResource("%Symb.txt");
    
    private File file12byte = new File(url12byte.getPath());
    private File file48byte = new File(url48byte.getPath());
    private File fileAccepted = new File(urlAcceptedFile.getPath());
    private File fileShort = new File(urlShort.getPath());
    private File fileExtensionNotAllowed = new File(urlFileType.getPath());
    private File fileLong = new File(urlLong.getPath());
    private File fileSymbols = new File(urlFileSumbols.getPath());
    private File fileMissingTitle = new File(urlMissingTitle.getPath());
    private File fileNull = null;

    @BeforeEach
    public void beforeEach() {
        testFileReq = new FileRequirements( symbols, maxLength, fileSizeBytes, extensionsAllowed);
        fileValidator = new FileValidator(testFileReq);
    }

    @Test
    void validateFilePositive() {
        boolean actual = fileValidator.validateFile(fileAccepted);
        assertEquals(true, actual);
    }
    
    @Test
    void validateFileNegativeSize() {
        boolean actual = fileValidator.validateFile(file48byte);
        assertEquals(false, actual);
    }
    
    @Test
    void validateFileNegativeLength() {
        boolean actual = fileValidator.validateFile(fileLong);
        assertEquals(false, actual);
    }
    
    @Test
    void validateFileNegativeSymbols() {
        boolean actual = fileValidator.validateFile(fileSymbols);
        assertEquals(false, actual);
    }

    @Test
    void validateExtensionNegative() {
        boolean actual = fileValidator.validateExtension(fileExtensionNotAllowed);
        assertEquals(false, actual);
    }
    @Test
    void validateExtensionPositive() {
        boolean actual = fileValidator.validateExtension(file12byte);
        assertEquals(true, actual);
    }
    @Test
    void validateExtensionNull() {
        boolean actual = fileValidator.validateExtension(fileNull);
        assertEquals(false, actual);
    }

    @Test
    void validateTitleLengthNegative() {
        boolean actual = fileValidator.validateTitleLength(fileLong.getName());
        assertEquals(false, actual);
    }
    @Test
    void validateTitleLengthPositive() {
        boolean actual = fileValidator.validateTitleLength(fileShort.getName());
        assertEquals(true, actual);
    }
    @Test
    void validateTitleMissingTitle() {
        boolean actual = fileValidator.validateTitleLength(fileMissingTitle.getName());
        assertEquals(false, actual);
    }
    @Test
    void validateTitleMissingFile() {
        boolean actual = fileValidator.validateTitleLength(fileMissingTitle.getName());
        assertEquals(false, actual);
    }

    @Test
    void validateFileSizePositive() {
        boolean actual = fileValidator.validateFileSize(file12byte);
        assertEquals(true, actual);
    }

    @Test
    void validateFileSizeNegative() {
        boolean actual = fileValidator.validateFileSize(file48byte);
        assertEquals(false, actual);
    }
    @Test
    void validateFileSizeNull() {
        boolean actual = fileValidator.validateFileSize(fileNull);
        assertEquals(false, actual);
    }

    @Test
    void validateTitleSymbolsAllowedNegative() {
        boolean actual = fileValidator.validateFileSymbols(fileSymbols.getName());
        assertEquals(false, actual);
    }

    @Test
    void validateTitleSymbolsAllowedPositive() {
        boolean actual = fileValidator.validateFileSymbols(file12byte.getName());
        assertEquals(true, actual);
    }
    @Test
    void validateTitleSymbolsAllowednull() {
        boolean actual = fileValidator.validateFileSymbols(null);
        assertEquals(false, actual);
    }
    @Test
    void validateTitleSymbolsAllowedEmpty() {
        boolean actual = fileValidator.validateFileSymbols(fileMissingTitle.getName());
        assertEquals(true, actual);
    }
}