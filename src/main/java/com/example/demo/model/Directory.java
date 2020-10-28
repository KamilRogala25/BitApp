package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Directory {

    private String path;


    public List<File> getAllFiles(String path) {
        File directory = new File(path);
        List<File> files = new ArrayList();
        File[] filesArray = directory.listFiles();
        files.addAll(Arrays.asList(filesArray));
        for (File file : filesArray) {
            if (file.isDirectory()) {
                files.addAll(getAllFiles(file.getAbsolutePath()));
            }
        }
        return files;
    }

    public List<File> getFilesOfExtension(String path, String extension) {
        Directory directory = new Directory();
        List<File> allFiles = directory.getAllFiles(path);
        List<File> results = new ArrayList();

        for (File file : allFiles) {
            if (FilenameUtils.getExtension(file.toString()).equalsIgnoreCase(extension)) {
                results.add(file);
            }
        }
        return results;
    }

    public String readFile(String path) throws IOException, OutOfMemoryError {
        File file = new File(path);
        byte[] fileBytes = Files.readAllBytes(Paths.get(path));
        StringBuilder sb = new StringBuilder("");
        for (byte b : fileBytes) {
            sb.append(b);
        }
        return sb.toString();
    }

    public String replaceSequence(String path, CharSequence toFindSequ, CharSequence toReplaceSequ) throws IOException {
        if (toFindSequ.length() > 80 || toReplaceSequ.length() > 80 || (toFindSequ.length() > 80 && toReplaceSequ.length() > 80)) {
            throw new IllegalStateException("Sekwencja za duża");
        }
        Directory directory = new Directory();
        String bytes = directory.readFile(path);
        return bytes.replace(toFindSequ, toReplaceSequ);
    }

    public static void main(String[] args) throws IOException {
        Directory directory = new Directory();
        directory.setPath("D:\\Programowanie\\Applications");
//        System.out.println(directory.getAllFiles(directory.getPath()));
//        System.out.println(directory.getFilesOfExtension(directory.getPath(), "txt"));
//        System.out.println(directory.getFilesOfExtension(directory.getPath(), "bmp"));
//        System.out.println(directory.getFilesOfExtension(directory.getPath(), "docx"));
//        System.out.println(directory.readFile("D:\\Programowanie\\Applications\\chuj\\Nowy folder\\aaa.bmp"));
//        System.out.println(directory.readFile("D:\\Programowanie\\Applications\\chuj\\Nowy folder\\bbb\\papieska.gif"));
//        System.out.println(directory.readFile("D:\\Pobrane\\Borat.2.Subsequent.Moviefilm.2020.HDRip.XviD.AC3-EVO[TGx]\\Borat.2.Subsequent.Moviefilm.2020.HDRip.XviD.AC3-EVO.avi").toString());
        System.out.println(directory.readFile("D:\\Pobrane\\Borat.2.Subsequent.Moviefilm.2020.HDRip.XviD.AC3-EVO[TGx]\\Borat.2.Subsequent.Moviefilm.2020.HDRip.XviD.AC3-EVO.avi"));
    }

}
