package org.controllers;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.daos.FileDAO;
import org.dtos.FileDTO;
import org.exceptions.ApiException;
import org.persistence.model.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.persistence.repository.FileDataRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String timestamp = dateFormat.format(new Date());

    @Autowired
    private FileDataRepository fileDataRepository;

    private final String uploadDir = Paths.get(System.getProperty("user.dir"), "uploads").toString();

    @PostMapping
    public int uploadFile(@RequestParam("file") MultipartFile file,
                          @RequestParam("description") String description,
                          @RequestParam("topic") String topic) throws IOException {

        // Check if the file is empty
        if (file.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "File is empty.", timestamp);
        }

        // Create the directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                throw new IOException("Failed to create the directory: " + uploadDir);
            }
        }


        // Get the original filename
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IOException("File name is missing.");
        }

        // Create a path to save the file
        Path path = Path.of(uploadDir + fileName);

        // Save the file to the directory
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Create a FileData object with description, topic, and file metadata
        FileData fileData = new FileData(description, topic, new File(path.toString()));

        // Persist the file entity using DAO
        FileData savedFile = fileDataRepository.save(fileData);

        // Convert to DTO and return response
        FileDTO dto = convertToDTO(savedFile);
        if (dto != null) {
            return HttpStatus.OK.getCode();
        } else {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), "Couldn't create file.", timestamp);
        }
    }

    public static FileDTO convertToDTO(FileData fileData) {
        return FileDTO.builder()
                .id(fileData.getId())
                //.folderPath(fileData.getFolderPath())
                .name(fileData.getName())
                .fileType(fileData.getFileType())
                .description(fileData.getDescription())
                .topic(fileData.getTopic())
                .build();
    }

    public static Handler getAllByTypeInPath(FileDAO dao) {
        return ctx -> {
            String folderPath = ctx.pathParam("folder_path");
            String fileType = ctx.pathParam("file_type");
            List<FileData> itemList = dao.getAllByTypeInPath(folderPath, fileType);
            List<FileDTO> dtoList = new ArrayList<>();
            for (FileData i : itemList) {
                dtoList.add(convertToDTO(i));
            }
            if (dtoList.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No files were found.", timestamp);
            } else {
                ctx.status(HttpStatus.OK).json(dtoList);
            }
        };
    }

    public static Handler getAllFilesInPath(FileDAO dao) {
        return ctx -> {
            String filePath = ctx.pathParam("folder_path");
            List<FileData> fileDataList = dao.getAllFilesInPath(filePath);
            List<FileDTO> dtoList = new ArrayList<>();
            for (FileData f : fileDataList) {
                dtoList.add(convertToDTO(f));
            }
            if (dtoList.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No files were found.", timestamp);
            } else {
                ctx.status(HttpStatus.OK).json(dtoList);
            }
        };
    }

    public static Handler delete(FileDAO dao) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            FileData foundFileData = dao.getById(id);
            if (foundFileData != null) {
                FileDTO dto = convertToDTO(foundFileData);
                dao.delete(dto.getId());
                ctx.status(HttpStatus.OK).json(dto);
            } else {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "FileData was not found: " + id, timestamp);
            }
        };
    }


    public static Handler getById(FileDAO dao) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            try {
                FileData foundFileData = dao.getById(id);
                if (foundFileData != null) {
                    FileDTO dto = convertToDTO(foundFileData);
                    if (dto != null) {
                        ctx.status(HttpStatus.OK).json(dto);
                    }
                } else {
                    throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "FileData was not found: " + id, timestamp);
                }
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST.getCode()).json("Invalid id format: " + e.getMessage());
            } catch (ApiException e) {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "Item was not found: " + id, timestamp);
            }
        };
    }

    /*public static Handler create(FileDAO dao) {
        return ctx -> {
            FileData fileData = ctx.bodyAsClass(FileData.class);
            FileData createdFileData = dao.create(fileData);
            FileDTO dto = convertToDTO(createdFileData);
            if (dto != null) {
                ctx.status(HttpStatus.OK).json(dto);
            } else {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), "Couldn't create fileData.", timestamp);
            }
        };
    }*/
    public static Handler create(FileDAO dao) {
        return ctx -> {
            // Get file from request
            FileData fileData = ctx.bodyAsClass(FileData.class);  // Get FileData from body

            // Check if the file is present
            if (fileData == null || fileData.getFilePath() == null) {
                throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "Missing file or file path.", timestamp);
            }

            // Assuming you have a method to handle file paths
            java.io.File file = new java.io.File(fileData.getFilePath());

            // Create the FileData object with the file
            FileData createdFileData = new FileData(fileData.getDescription(),
                    fileData.getTopic(), file);

            // Persist the file entity using DAO
            FileData savedFile = dao.create(createdFileData);  // DAO to save the file

            // Convert to DTO if needed and respond
            FileDTO dto = convertToDTO(savedFile);
            if (dto != null) {
                ctx.status(HttpStatus.OK.getCode()).json(dto);
            } else {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), "Couldn't create file.", timestamp);
            }
        };
    }


    public static Handler update(FileDAO fileDAO) {
        return ctx -> {
            FileData fileData = ctx.bodyAsClass(FileData.class);
            if (fileData != null) {
                FileData i = fileDAO.update(fileData);
                if (i != null) {
                    FileDTO dto = convertToDTO(fileData);
                    ctx.json(dto);
                } else {
                    throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "FileData not found for id: " + fileData.getId(), timestamp);
                }
            }
        };
    }
}