package org.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class FileRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private char[] symbolsNotAllowed_Title;
    private int charLength_Title;
    private double fileSizeBytes;

    @ElementCollection
    private Set<String> fileExtensionAllowed = new HashSet<>();

    public FileRequirements(char[] symbolsNotAllowed_Title, int charLength_Title, double fileSizeBytes, Set<String> fileExtension) {
        this.symbolsNotAllowed_Title = symbolsNotAllowed_Title;
        this.charLength_Title = charLength_Title;
        this.fileSizeBytes = fileSizeBytes;
        this.fileExtensionAllowed = fileExtension;
    }
}
