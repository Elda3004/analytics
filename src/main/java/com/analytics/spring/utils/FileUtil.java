package com.analytics.spring.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileUtil {

    public static File getResourceAsFile(String relativeFolderPath) throws FileNotFoundException {
        return ResourceUtils.getFile(String.format("classpath:%s",relativeFolderPath));
    }

    public static File[] getAllFilesInFolder(String folderPath) throws FileNotFoundException {
        File directory = getResourceAsFile(folderPath);
        return directory.listFiles();
    }
}
