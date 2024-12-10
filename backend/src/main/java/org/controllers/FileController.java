package org.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.daos.FileDAO;
import org.daos.FolderDAO;
import org.daos.RoleDAO;
import org.dtos.FileDTO;
import org.dtos.UserFilePermInFolderDTO;
import org.persistence.model.Permissions;
import org.persistence.model.SubRole;
import org.exceptions.ApiException;
import org.persistence.model.File;
import org.util.TokenUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String timestamp = dateFormat.format(new Date());

    private static RoleDAO roleDAO = new RoleDAO();
    private static FolderDAO folderDAO = new FolderDAO();

    public static FileDTO convertToDTO(File file) {
        return FileDTO.builder()
                .id(file.getId())
                .folderPath(file.getFolderPath())
                .name(file.getName())
                .fileType(file.getFileType())
                .build();
    }

    public static Handler getAllByTypeInPath(FileDAO dao) {
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

    public static Handler getAllFilesInPath(FileDAO dao) {
        return ctx -> {
            String filePath = ctx.pathParam("folder_path");
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

    public static Handler delete(FileDAO dao) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
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


    public static Handler getById(FileDAO dao) {
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

    public static Handler create(FileDAO dao) {
        return ctx -> {

            File file = ctx.bodyAsClass(File.class);
            File createdFile = dao.create(file);
            FileDTO dto = convertToDTO(createdFile);

            if (dto != null) {
                System.out.println("Det gik godt ven.");
                ctx.status(HttpStatus.OK).json(dto);
            } else {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), "Couldn't create file.", timestamp);
            }
        };
    }

    public static Handler update(FileDAO fileDAO) {
        return ctx -> {
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

    public static Handler getFilePermissionsForUserInFolder(FileDAO fileDAO) {

        return ctx -> {
            var folderID = Integer.parseInt(ctx.pathParam("folder_id"));
            var userID = getUserIdFromToken(ctx);

            try {

                List<SubRole> subRolesOfUser = roleDAO.getUserSubRoles(Integer.parseInt(userID));

                for (SubRole sR : subRolesOfUser){
                    List<Permissions> userFilePermissionsInFolder = new ArrayList<>();

                    Permissions perm = folderDAO.getPermissions(folderID, sR);

                    userFilePermissionsInFolder.add(perm);
                }

                UserFilePermInFolderDTO userFilePermissionsInFolderDTO = new UserFilePermInFolderDTO(userFilePermissionsInFolder);

                ctx.status(200).json(userFilePermissionsInFolderDTO);

            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST.getCode()).json("Invalid id format: " + e.getMessage());

            } catch (ApiException e) {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "Item was not found: " + folderID, timestamp);
            }
        };
    }

    private static String getUserIdFromToken(Context ctx) throws ParseException {
        var header = ctx.headerMap();
        var token = (header.get("Authorization").split(" "))[1];
        var userID = TokenUtils.getUserIdFromToken(token);
        return userID;
    }
}