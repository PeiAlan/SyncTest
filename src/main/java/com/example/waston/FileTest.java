package com.example.waston;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/10/28 15:12
 * @since 1.0
 **/
@Slf4j
public class FileTest {
    private static final String DIRECTORY_PATH = "/Users/ellisonpei/Desktop/backup";
    private static final String ENCODING = "UTF-8";

    public static void main(String[] args) {
        List<String> filesFromDirectory = getFilesFromDirectory(DIRECTORY_PATH);
        for (String filePath : filesFromDirectory) {
            WatsonsFileUtil.getListOfFile(filePath, ENCODING);
        }
        log.info("执行结束");
    }

    private static List<String> getFilesFromDirectory(String directoryPath){
        List<String> filePathList = new ArrayList<>();
        List<File> files = FileUtil.loopFiles(directoryPath);
        files.forEach(file -> filePathList.add(file.getAbsolutePath()));
        return filePathList;
    }
}
