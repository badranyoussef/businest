package org.controllers;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.daos.FileDAO;
import org.dtos.FileDTO;
import org.exceptions.ApiException;
import org.persistence.model.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.persistence.repository.FileDataRepository;
import org.dtos.FileCreateRequest;

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

public class FileController {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String timestamp = dateFormat.format(new Date());
    private static final String uploadDir = "./backend/src/main/resources/uploads";

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

    public static Handler create(FileDAO dao) {
        return ctx -> {
            System.out.println("Starting file creation...");

            // Parse the request body into FileCreateRequest
            FileCreateRequest request = ctx.bodyAsClass(FileCreateRequest.class);
            System.out.println("Received request: " + request);

            if (request.getFilePath() == null || request.getFilePath().trim().isEmpty()) {
                throw new ApiException(HttpStatus.BAD_REQUEST.getCode(), "File path is required.", timestamp);
            }

            // Get the source file
            File sourceFile = new File(request.getFilePath());
            System.out.println("Source file path: " + sourceFile.getAbsolutePath());

            if (!sourceFile.exists()) {
                throw new ApiException(HttpStatus.BAD_REQUEST.getCode(),
                        "Source file not found: " + sourceFile.getAbsolutePath(), timestamp);
            }

            // Create uploads directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getCode(),
                            "Failed to create upload directory", timestamp);
                }
            }

            // Generate unique filename
            String uniqueFilename = System.currentTimeMillis() + "_" + sourceFile.getName();
            Path targetLocation = Paths.get(uploadDir + uniqueFilename);
            System.out.println("Target location: " + targetLocation);

            try {
                // Copy file to upload directory
                Files.copy(sourceFile.toPath(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied successfully");

                // Create FileData object
                FileData fileData = new FileData(
                        request.getDescription(),
                        request.getTopic(),
                        targetLocation.toFile()
                );
                System.out.println("Created FileData: " + fileData);

                // Save using DAO
                FileData savedFile = dao.create(fileData);
                System.out.println("Saved FileData: " + savedFile);

                // Convert to DTO and return
                FileDTO dto = convertToDTO(savedFile);
                ctx.status(HttpStatus.OK).json(dto);
            } catch (Exception e) {
                System.err.println("Error occurred: " + e.getMessage());
                e.printStackTrace();

                // Clean up the file if there was an error
                Files.deleteIfExists(targetLocation);

                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getCode(),
                        "Failed to create file: " + e.getMessage(), timestamp);
            }
        };
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
