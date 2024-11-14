package org.util;

import org.entities.FileRequirements;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileValidator {

    private FileRequirements fileRequirements;

    public FileValidator(FileRequirements _fileRequirements) {
        fileRequirements = _fileRequirements;
    }

    public boolean validateFile(File file) {
        return validateExtension(file) == validateTitleLength(file.getName());
    }

    public boolean validateExtension(File file) {
        if (file == null) {
            return false;
        }
        String fileName = file.getName();
        for (String format : fileRequirements.getFileExtensionAllowed()) {
            if (fileName.endsWith(format)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateTitleLength(String name) {
        if (getTitleLengthWithoutExtension(name) <= 0 || name == null || name.isEmpty()
                || getTitleLengthWithoutExtension(name) > fileRequirements.getCharMaxLength_Title()) {
            return false;
        }
        return true;
    }

    private int getTitleLengthWithoutExtension(String name) {
        return name.lastIndexOf(".");
    }

    public boolean validateFileSize(File file) {
        if (file == null) {
            return false;
        }
        return file.length() <= fileRequirements.getFileSizeBytes();
    }

    public boolean validateFileSymbols(String name) {
        if (name == null) {
            return false;
        }
        name = name.substring(0, getTitleLengthWithoutExtension(name));
        for (char symbol : fileRequirements.getForbiddenSymbols_Title()) {
            if (name.contains(String.valueOf(symbol))) {
                return false;
            }
        }
        return true;
    }
}
