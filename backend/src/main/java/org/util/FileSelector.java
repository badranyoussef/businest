package org.util;

import javax.swing.JFileChooser;
import java.io.File;

public class FileSelector {

    public void selectFile(String filePath) {
        JFileChooser fileChooser = new JFileChooser();

        if (filePath != null && !filePath.isEmpty()) {
            fileChooser.setCurrentDirectory(new File(filePath));
        }

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("FileData selection was canceled.");
        }
    }
}
