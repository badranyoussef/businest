package org.persistence.model;

import jakarta.persistence.*;
import lombok.*;

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

    @ElementCollection
    @Column(name = "forbidden_symbols_title")
    private char[] forbiddenSymbols_Title;

    @Column(name = "char_max_length_title")
    private int charMaxLength_Title;

    @Column(name = "file_size_bytes")
    private double fileSizeBytes;

    @ElementCollection
    private Set<String> fileExtensionAllowed = new HashSet<>();

    public FileRequirements(char[] forbiddenSymbols_Title, int charMaxLength_Title, double fileSizeBytes, Set<String> fileExtension) {
        this.forbiddenSymbols_Title = forbiddenSymbols_Title;
        this.charMaxLength_Title = charMaxLength_Title;
        this.fileSizeBytes = fileSizeBytes;
        this.fileExtensionAllowed = fileExtension;
    }
}
