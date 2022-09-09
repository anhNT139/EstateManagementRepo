package com.kepler.utils;

import com.kepler.exception.FileHandlingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.kepler.constant.SystemConstant.File.FILE_UPLOAD_ROOT_FOLDER;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileUtils {

    private static final String rootFolder;
    private static final String separator;

    static {
        Properties properties;
        try (InputStream is = FileUtils.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            throw new FileHandlingException("Lỗi khi xử lý file (load property)");
        }
        rootFolder = properties.get(FILE_UPLOAD_ROOT_FOLDER).toString();
        separator = File.separator;
    }

    public static String generatePath() {
        LocalDateTime now = LocalDateTime.now();
        List<String> subPaths = new ArrayList<>(Arrays.asList(
                String.valueOf(now.getYear()),
                String.valueOf(now.getMonth().getValue()),
                String.valueOf(now.getDayOfMonth())
            )
        );
        return String.join(separator, subPaths);
    }

    public static void createDirectoryIfNotExist(String pathStr) {
        Path path = Paths.get(pathStr);
        if (! Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new FileHandlingException("Lỗi khi xử lý file (create new directories)");
            }
        }
    }

    public static void copyToDirectory(MultipartFile source, String targetDirectory, String fileName) {
        try {
            String directoryPath = String.join(separator, rootFolder, targetDirectory);
            createDirectoryIfNotExist(directoryPath);

            Files.copy(source.getInputStream(), Paths.get(directoryPath + separator + fileName), REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileHandlingException("Lỗi khi xử lý file (copy to directories)");
        }
    }

    public static void delete(String path, String fileName) {
        try {
            Files.deleteIfExists(Paths.get(String.join(separator, rootFolder, path, fileName)));
        } catch (IOException e) {
            throw new FileHandlingException("Lỗi khi xử lý file (delete)");
        }
    }
}
