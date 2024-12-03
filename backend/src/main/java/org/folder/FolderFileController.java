package org.folder;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.persistence.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FolderFileController {

    private static final Logger logger = LoggerFactory.getLogger(FolderFileController.class);
    private static final String ERROR_MESSAGE = "An error occurred";

    // Get all files by folder ID
    public static Handler getAllFilesByFolderId(FileDao fileDAO, FolderDAO folderDAO) {
        return ctx -> {
            try {
                int folderId = Integer.parseInt(ctx.pathParam("folder_id"));
                Folder folder = folderDAO.getById(folderId);

                if (folder == null) {
                    String message = "Folder not found: ID " + folderId;
                    logger.warn(message);
                    ctx.status(HttpStatus.NOT_FOUND).json(Map.of("message", message));
                    return;
                }

                Set<File> files = folder.getFiles();
                if (files.isEmpty()) {
                    String message = "No files found in folder: " + folder.getName();
                    logger.info(message);
                    ctx.status(HttpStatus.NOT_FOUND).json(Map.of("message", message));
                } else {
                    logger.info("Files successfully retrieved for folder ID {}: {}", folderId, files.size());
                    ctx.status(HttpStatus.OK).json(files);
                }
            } catch (NumberFormatException e) {
                String message = "Invalid folder ID format.";
                logger.warn(message, e);
                ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("message", message, "error", e.getMessage()));
            } catch (Exception e) {
                logger.error("Error fetching files for folder ID.", e);
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("message", ERROR_MESSAGE, "error", e.getMessage()));
            }
        };
    }

    // Get all folders
    public static Handler getAllFolders(FolderDAO folderDAO) {
        return ctx -> {
            try {
                List<Folder> folders = folderDAO.getAllFolders();
                if (folders.isEmpty()) {
                    String message = "No folders found.";
                    logger.info(message);
                    ctx.status(HttpStatus.NOT_FOUND).json(Map.of("message", message));
                } else {
                    logger.info("Folders successfully retrieved.");
                    ctx.status(HttpStatus.OK).json(folders);
                }
            } catch (Exception e) {
                logger.error("Error fetching all folders.", e);
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("message", ERROR_MESSAGE, "error", e.getMessage()));
            }
        };
    }

    // Create a new folder
    public static Handler createFolder(FolderDAO folderDAO) {
        return ctx -> {
            try {
                Folder folderInput = ctx.bodyAsClass(Folder.class);

                if (folderInput.getId() == 0 || folderInput.getName() == null) {
                    String message = "Folder ID or name is missing.";
                    logger.warn(message);
                    ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("message", message));
                    return;
                }

                Folder createdFolder = folderDAO.createOrUpdateFolder(folderInput.getId(), folderInput.getName());

                logger.info("Folder created or updated successfully: {}", createdFolder);
                ctx.status(HttpStatus.CREATED).json(createdFolder);
            } catch (Exception e) {
                logger.error("Error creating or updating folder.", e);
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("message", ERROR_MESSAGE, "error", e.getMessage()));
            }
        };
    }

    // Get a folder by ID
    public static Handler getFolderById(FolderDAO folderDAO) {
        return ctx -> {
            try {
                int folderId = Integer.parseInt(ctx.pathParam("folder_id"));
                Folder folder = folderDAO.getById(folderId);

                if (folder == null) {
                    String message = "Folder not found: ID " + folderId;
                    logger.warn(message);
                    ctx.status(HttpStatus.NOT_FOUND).json(Map.of("message", message));
                } else {
                    logger.info("Folder successfully retrieved: {}", folder);
                    ctx.status(HttpStatus.OK).json(folder);
                }
            } catch (NumberFormatException e) {
                String message = "Invalid folder ID format.";
                logger.warn(message, e);
                ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("message", message, "error", e.getMessage()));
            } catch (Exception e) {
                logger.error("Error fetching folder.", e);
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("message", ERROR_MESSAGE, "error", e.getMessage()));
            }
        };
    }

    // Add a file to a folder
    public static Handler addFileToFolder(FileDao fileDAO) {
        return ctx -> {
            try {
                int folderId = Integer.parseInt(ctx.pathParam("folder_id"));
                File file = ctx.bodyAsClass(File.class);

                if (file.getName() == null || file.getFileType() == null) {
                    String message = "File name or file type is missing.";
                    logger.warn(message);
                    ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("message", message));
                    return;
                }

                File addedFile = fileDAO.addFileToFolder(folderId, file);

                logger.info("File added to folder with ID {}: {}", folderId, addedFile);
                ctx.status(HttpStatus.CREATED).json(addedFile);
            } catch (IllegalArgumentException e) {
                logger.warn("Folder not found: {}", e.getMessage());
                ctx.status(HttpStatus.NOT_FOUND).json(Map.of("message", e.getMessage()));
            } catch (Exception e) {
                logger.error("Error adding file to folder.", e);
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of("message", ERROR_MESSAGE, "error", e.getMessage()));
            }
        };
    }
}
