package org.file;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileValidator {

    public boolean validateFormat(File file) {
        List<String> approvedFormats = Arrays.asList(".txt", ".pdf");

        String fileName = file.getName();
        for (String format : approvedFormats) {
            if (fileName.endsWith(format)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateInformation(String name) {
        if (name == null || name.isEmpty() || name.length() < 3) {
            return false;
        }
        return true;
    }
}
