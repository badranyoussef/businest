package org.controllers;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import org.daos.FileDAO;
import org.dtos.FileDTO;
import org.exceptions.ApiException;
import org.persistence.model.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class FileController {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String timestamp = dateFormat.format(new Date());

    private FileDAO fileDAO;

    public FileController(EntityManagerFactory emf) {
        fileDAO = FileDAO.getInstance(emf);

    }

    public FileDTO convertToDTO(File file) {
        return FileDTO.builder()
                //.id(file.getId())
                .folder_path(file.getFolderPath())
                .name(file.getName())
                .file_type(file.getFileType())
                .build();
    }

    public Handler getAllByTypeInPath(FileDAO dao) {
        return ctx -> {
            String folderPath = ctx.pathParam("folder_path");
            String fileType = ctx.pathParam("file_type");
            List<File> itemList = dao.getAllByTypeInPath(folderPath, fileType);
            List<FileDTO> dtoList = new ArrayList<>();
            for (File i : itemList) {
                dtoList.add(convertToDTO(i));
            }
            if (dtoList.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No files were found.", timestamp);
            } else {
                ctx.status(HttpStatus.OK).json(dtoList);
            }
        };
    }

    public Handler getAllFilesInPath(FileDAO dao) {
        return ctx -> {
            String filePath = ctx.pathParam("path");
            List<File> fileList = dao.getAllFilesInPath(filePath);
            List<FileDTO> dtoList = new ArrayList<>();
            for (File f : fileList) {
                dtoList.add(convertToDTO(f));
            }
            if (dtoList.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No files were found.", timestamp);
            } else {
                ctx.status(HttpStatus.OK).json(dtoList);
            }
        };
    }
/*
    public Handler delete(FileDAO dao) {
        return ctx -> {
            String file_id = ctx.pathParam("id");
            int id = ctx.bodyAsClass(Integer.class);
            File foundFile = dao.getById(id);
            if (foundFile != null) {
                FileDTO dto = convertToDTO(foundFile);
                dao.delete(dto.getId());
                ctx.status(HttpStatus.OK).json(dto);
            } else {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "File was not found: " + id, timestamp);
            }
        };
    }


    public Handler getById(FileDAO dao) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            try {
                File foundFile = dao.getById(id);
                if (foundFile != null) {
                    FileDTO dto = convertToDTO(foundFile);
                    if (dto != null) {
                        ctx.status(HttpStatus.OK).json(dto);
                    }
                } else {
                    throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "File was not found: " + id, timestamp);
                }
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST.getCode()).json("Invalid id format: " + e.getMessage());
            } catch (ApiException e) {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "Item was not found: " + id, timestamp);
            }
        };
    }

 */

    public Handler create() {
        //System.out.println("Filecontroller create method called");
        return ctx -> {
            System.out.println("GOT TO HERE");
            FileDTO fileDTO = ctx.bodyAsClass(FileDTO.class);

            File file = new File(fileDTO);

            File createdFile = fileDAO.create(file);
            FileDTO dto = convertToDTO(createdFile);
            if (dto != null) {
                System.out.println("Det gik godt ven.");
                ctx.status(HttpStatus.OK).json(dto);
            } else {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), "Couldn't create file.", timestamp);
            }
        };
    }

    public Handler update(FileDAO fileDAO) {
        return ctx -> {
            String id = ctx.pathParam("id");
            File file = ctx.bodyAsClass(File.class);
            if (file != null) {
                File i = fileDAO.update(file);
                if (i != null) {
                    FileDTO dto = convertToDTO(file);
                    ctx.json(dto);
                } else {
                    throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "File not found for id: " + file.getId(), timestamp);
                }
            }
        };
    }
}
