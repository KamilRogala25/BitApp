package com.example.demo.model;


import com.example.demo.exceptions.DirectoryIsEmptyExcepion;
import com.example.demo.exceptions.SelectionIsEmptyException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileModifier {

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

    public List<File> getFilesOfExtension(String path, String extension) throws DirectoryIsEmptyExcepion, SelectionIsEmptyException {
        if (path.isEmpty()){
            throw new SelectionIsEmptyException("Proszę wskazać poprawną ścieżkę.");
        }
        FileModifier fileModifier = new FileModifier();
        List<File> allFiles = fileModifier.getAllFiles(path);
        List<File> results = new ArrayList();

        for (File file : allFiles) {
            String name = file.getAbsolutePath();
            if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase(extension)) {
                results.add(file);
            }
        }
        if (results.isEmpty()) {
            throw new DirectoryIsEmptyExcepion("Nie znaleziono żadnego pliku typu" + extension + ".");
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

    public void createBackup(List<File> toCopy) throws IOException {

        String backupPath = "D:\\Applications\\BitApp\\Backups\\";
        List<File> backup = new ArrayList<>();
        for (File file: toCopy){
            String newPath = backupPath + file.getAbsolutePath().substring(3);
            backup.add(new File(newPath));
            FileUtils.copyFile(file, new File(newPath));
        }

    }

    public String replaceSequence(String path, CharSequence toFindSequ, CharSequence toReplaceSequ) throws IOException {
        if (toFindSequ.length() > 80 || toReplaceSequ.length() > 80 || (toFindSequ.length() > 80 && toReplaceSequ.length() > 80)) {
            throw new IllegalStateException("Sekwencja jest za duża.");
        }
        if (toFindSequ.equals(toReplaceSequ)){
            throw new IllegalStateException("Sekwencje są równe.");
        }
        if (path.isEmpty() || toFindSequ==null || toReplaceSequ==null) {
            throw new IllegalStateException("Podano puste dane.");
        }
        FileModifier fileModifier = new FileModifier();
        String bytes = fileModifier.readFile(path);
        return bytes.replace(toFindSequ, toReplaceSequ);

    }


    public String modifyFiles(String path, String extension, CharSequence toFindSequ, CharSequence toReplaceSequ) throws IOException, DirectoryIsEmptyExcepion, SelectionIsEmptyException {
        FileModifier fileModifier = new FileModifier(path);
        List<File> files = fileModifier.getFilesOfExtension(fileModifier.getPath(), extension);
        String tempBytes;
        fileModifier.createBackup(files);

        int counterChanged = 0;
        int counterUnChanged = 0;

        for (File file : files) {
            if (!files.isEmpty()) {
                List<Byte> bytes = new ArrayList<>();
                System.out.println(fileModifier.readFile(file.getAbsolutePath()));
                tempBytes = fileModifier.replaceSequence(file.getAbsolutePath(), toFindSequ, toReplaceSequ);
                System.out.println(tempBytes);
                if (fileModifier.readFile(file.getAbsolutePath()).equals(tempBytes)){
                    counterUnChanged++;
                }
                else {
                    String tempPath = file.getAbsolutePath();
                    file.delete();
                    byte[] result = tempBytes.getBytes();
                    File resultFile = new File(tempPath);
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(resultFile);
                        fos.write(result);
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    } finally {
                        try{
                            if (fos !=null){
                                fos.close();
                            }
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    counterChanged++;
                }
            }
        }
        return "Zmodyfikowano " + counterChanged + " plików typu " + extension + ". Nie zmodyfikowano " + counterUnChanged + " plików typu " + extension + ".";
    }

    public static void main(String[] args) throws IOException, SelectionIsEmptyException, DirectoryIsEmptyExcepion {
        FileModifier fileModifier = new FileModifier();
        fileModifier.setPath("D:\\Programowanie\\Applications");
//        System.out.println(directory.getAllFiles(directory.getPath()));
//        System.out.println(fileModifier.getFilesOfExtension(fileModifier.getPath(), "txt"));
        System.out.println(fileModifier.modifyFiles(fileModifier.getPath(),"txt","1","2"));
        System.out.println(fileModifier.modifyFiles(fileModifier.getPath(),"gif","2","1"));
//        System.out.println(directory.getFilesOfExtension(directory.getPath(), "bmp"));
//        System.out.println(directory.getFilesOfExtension(directory.getPath(), "docx"));
//        System.out.println(fileModifier.readFile("D:\\Programowanie\\Applications\\chuj\\Nowy folder\\aaa.bmp"));
//        System.out.println(fileModifier.readFile("D:\\Programowanie\\Applications\\chuj\\Nowy folder\\bbb\\papieska.gif"));
//        System.out.println(directory.readFile("D:\\Pobrane\\Borat.2.Subsequent.Moviefilm.2020.HDRip.XviD.AC3-EVO[TGx]\\Borat.2.Subsequent.Moviefilm.2020.HDRip.XviD.AC3-EVO.avi").toString());
        //System.out.println(fileModifier.readFile("D:\\Pobrane\\Borat.2.Subsequent.Moviefilm.2020.HDRip.XviD.AC3-EVO[TGx]\\Borat.2.Subsequent.Moviefilm.2020.HDRip.XviD.AC3-EVO.avi"));
    }

}
