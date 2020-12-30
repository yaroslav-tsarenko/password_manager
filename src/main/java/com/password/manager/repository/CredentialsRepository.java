package com.password.manager.repository;

import com.password.manager.cache.MemoryCache;
import com.password.manager.file.Reader;
import com.password.manager.file.Writer;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.password.manager.util.Constants.BASE_DIR;
import static com.password.manager.util.Constants.REPO_FILE_NAME;

public class CredentialsRepository {

    private static final Logger log = Logger.getLogger(CredentialsRepository.class);

    public static void save(String record) {
        String path = getAbsolutePath(BASE_DIR, REPO_FILE_NAME);
        String dir = getPathToDir(path, REPO_FILE_NAME);
        try {
            if (checkRepo()) Files.createDirectories(Paths.get(dir));
            Writer.writeOutputToFileSoftMode(record, path);
        } catch (IOException e) {
            log.error(e);
        }
        if (MemoryCache.hasProperty("storage_file_path") && MemoryCache.hasProperty("storage_dir_path")) {
            MemoryCache.setProperty("storage_file_path", path);
            MemoryCache.setProperty("storage_dir_path", dir);
        }
    }

    public static String getOneByServiceName(String serviceName) {
        if (prepareRepo()) {
            String input = null;
            try {
                input = Reader.readInputFromFile(MemoryCache.getProperty("storage_file_path"));
            } catch (IOException e) {
                log.error(e);
            }
            String[] lines = input.split("\n");
            for (int i = 0; i < lines.length; i++) {
                if (existsByName(serviceName, lines[i])) return lines[i];
            }
        }
        return "storage not found";
    }

    private static boolean existsByName(String name, String line) {
        String[] words = line.split("\\s");
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(name)) return true;
        }
        return false;
    }

    public static boolean checkRepo() {
        String pathToFile = getAbsolutePath(BASE_DIR, REPO_FILE_NAME);
        String pathToDir = getPathToDir(pathToFile, REPO_FILE_NAME);
        boolean existsDir = Files.isDirectory(Paths.get(pathToDir));
        boolean existsFile = Files.isRegularFile(Paths.get(pathToFile));
        if (existsDir && existsFile) {
            MemoryCache.setProperty("storage_file_path", pathToFile);
            MemoryCache.setProperty("storage_dir_path", pathToDir);
        }
        return !existsDir || !existsFile;
    }

    private static boolean prepareRepo() {
        if (MemoryCache.hasProperty("storage_file_path") && MemoryCache.hasProperty("storage_dir_path")) {
            return true;
        } else return !checkRepo();
    }

    private static String getAbsolutePath(String path, String fileName) {
        return Path.of(path.concat(File.separator).concat(fileName)).toAbsolutePath().toString();
    }

    private static String getPathToDir(String path, String fileName) {
        return path.split(fileName)[0];
    }
}
