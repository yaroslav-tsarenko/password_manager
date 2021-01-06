package com.password.manager.config;

import com.password.manager.cache.MemoryCache;
import com.password.manager.config.exception.ConfigException;
import com.password.manager.file.Reader;
import com.password.manager.file.Writer;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.password.manager.util.Constants.BASE_DIR;
import static com.password.manager.util.Constants.FILE_NAME;

public class ConfigLoader {

    private static final Logger log = Logger.getLogger(ConfigLoader.class);

    public static void saveConfig(String src) throws IOException {
        String path = getAbsolutePath(BASE_DIR, FILE_NAME);
        String dir = getPathToDir(path, FILE_NAME);
        if (checkProps()) {
            Files.createDirectories(Paths.get(dir));
        }
        Writer.writeOutputToFileSoftMode(src, path);
        MemoryCache.setProperty("property_file_path", path);
        MemoryCache.setProperty("resource_dir_path", dir);
    }

    public static String loadConfig() {
        String pathToFile = getAbsolutePath(BASE_DIR, FILE_NAME);
        if (checkProps())
            throw new ConfigException("can't find app properties");
        String input = "";
        try {
            input = Reader.readInputFromFile(pathToFile);
        } catch (IOException e) {
            log.error(e);
        }
        return input;
    }

    public static boolean checkProps() {
        String pathToFile = getAbsolutePath(BASE_DIR, FILE_NAME);
        String pathToDir = getPathToDir(pathToFile, FILE_NAME);
        boolean existsDir = Files.isDirectory(Paths.get(pathToDir));
        boolean existsFile = Files.isRegularFile(Paths.get(pathToFile));
        if (!(MemoryCache.hasProperty("property_file_path") && MemoryCache.hasProperty("resource_dir_path"))) {
            MemoryCache.setProperty("property_file_path", pathToFile);
            MemoryCache.setProperty("resource_dir_path", pathToDir);
        }
        return !existsDir || !existsFile;
    }

    public static boolean checkPass() {
        String file = "";
        try {
            file = Reader.readInputFromFile(MemoryCache.getProperty("property_file_path"));
        } catch (IOException e) {
            log.error(e);
        }
        String[] lines = file.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] words = lines[i].split("=");
            for (int j = 0; j < words.length; j++) {
                if (words[j].equals("admin_password")) return true;
            }
        }
        return false;
    }

    private static String getAbsolutePath(String path, String fileName) {
        return Path.of(path.concat(File.separator).concat(fileName)).toAbsolutePath().toString();
    }

    private static String getPathToDir(String path, String fileName) {
        return path.split(fileName)[0];
    }
}
